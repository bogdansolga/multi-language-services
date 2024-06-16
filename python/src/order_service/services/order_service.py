import logging
from datetime import datetime
from random import randint

import requests
from common.dto.order import OrderDTO
from common.helper.asynch import run_async
from common.message.command.order import (
    ChargeOrderCommand,
    CreateOrderCommand,
    Currency,
)
from common.message.event.order import OrderCreatedEvent
from flask import current_app

from order_service.models import Order
from order_service.repositories.order import OrderRepository


class OrderService:
    def __init__(self):
        self.logger = logging.getLogger(__name__)

    # creating an order received from a REST endpoint (UI, testing app etc)
    def create_order_rest(self, order_dto: OrderDTO):
        order_repository = OrderRepository()

        order = Order(
            customer_id=order_dto.customer_id, total=order_dto.get_order_total(), creation_date=datetime.now()
        )
        order_id = order_repository.save(order)

        self.logger.info(
            f"Creating an order for the customer with the ID {order.customer_id}, for {order.order_items} items..."
        )

        run_async(
            (
                lambda: (
                    self.publish_charge_order(
                        order.customer_id, order_id, order.total
                    ),
                    self.publish_order_created_event(order.customer_id, order_id),
                )
            )
        )

    def publish_charge_order(self, customer_id, order_id, order_total):
        producer = current_app.extensions["producer"]
        producer.publish_charge_order_command(
            ChargeOrderCommand(
                message_id=self.__get_next_message_id(),
                customer_id=customer_id,
                order_id=order_id,
                order_total=order_total,
                currency=Currency.EUR,
            )
        )

    def publish_order_created_event(self, customer_id, order_id):
        producer = current_app.extensions["producer"]
        producer.publish_order_created_event(
            OrderCreatedEvent(
                message_id=self.__get_next_message_id(),
                event_id=self.__get_next_event_id(),
                customer_id=customer_id,
                order_id=order_id,
            )
        )

    # creating an order received from a messaging endpoint (upstream system, 3rd party application etc)
    def create_order_messaging(self, create_order_command: CreateOrderCommand):
        self.logger.info(
            f"Creating an order for the product {create_order_command.product_name}, for the customer with the ID {create_order_command.customer_id}..."
        )

        order = Order(
            customer_id=create_order_command.customer_id,
            total=create_order_command.order_total,
            creation_date=datetime.now()
        )
        order_repository = OrderRepository()

        order_id = order_repository.save(order)

        self.publish_charge_order(order.customer_id, order_id, order.total)
        self.publish_order_created_event(order.customer_id, order_id)


    def charge_order(self, customer_id, order_id):
        # Constructing the URL
        url = f"{current_app.config["BILLING_SERVICE_ENDPOINT"]}/{customer_id}/{order_id}"

        try:
            response = requests.post(url)
            self.logger.info("Got the response: %s", response.text)
            return response

        except requests.RequestException as e:
            self.logger.error("Error occurred while making the request: %s", e)
            return None

    def __get_next_message_id(self):
        # Generate a random message ID (for demonstration purposes)
        return randint(0, 2**64 - 1)

    def __get_next_event_id(self):
        # Generate a random event ID (for demonstration purposes)
        return randint(0, 2**64 - 1)
