import logging
import os
from random import randint
from typing import List

from common.dto.order import PaymentDTO
from common.message.command.order import ChargeOrderCommand
from common.message.event.order import OrderChargedEvent, OrderNotChargedEvent

from billing_service.producer import Producer

from .payment_service import PaymentService

class BillingService:
    def __init__(
        self,
    ):
        self.logger = logging.getLogger(__name__)

    def charge_order(self, charge_order_command: ChargeOrderCommand):
        customer_id = charge_order_command.customer_id
        order_id = charge_order_command.order_id
        order_total = charge_order_command.order_total

        self.logger.info(
            f"Charging the customer with the ID {customer_id}, for the order with ID {order_id}, "
            f"for {order_total} {charge_order_command.currency}..."
        )

        used_payment_method = self.__get_payment_method()
        payment_service = PaymentService()
        order_charging_status = payment_service.charge(used_payment_method, order_total)
        producer = Producer(os.getenv("KAFKA_BROKERS", "localhost:9092"))

        if order_charging_status.successful:
            self.logger.info(
                f"The customer {customer_id} was successfully charged for the order {order_id}"
            )

            producer.publish_order_charged_event(
                OrderChargedEvent(
                    message_id=self.__get_next_message_id(),
                    event_id=self.__get_next_event_id(),
                    customer_id=customer_id,
                    order_id=order_id,
                )
            )
        else:
            failure_reason = (
                order_charging_status.failure_reason or "Cannot charge the card"
            )
            self.logger.warn(
                f"The customer {customer_id} could not be charged for the order {order_id} - "
                f"'{failure_reason}'"
            )

            producer.publish_order_not_charged_event(
                OrderNotChargedEvent(
                    message_id=self.__get_next_message_id(),
                    event_id=self.__get_next_event_id(),
                    customer_id=customer_id,
                    order_id=order_id,
                    reason=failure_reason,
                )
            )

    def charge_customer(self, customer_id: int, order_id: int) -> str:
        return (
            "The customer was charged"
            if randint(0, 1) == 0
            else "The charging has failed"
        )

    def get_payments_for_customer(self, customer_id: int) -> List[PaymentDTO]:
        return []

    def __get_payment_method(self) -> int:
        return randint(0, 19)

    def __get_next_message_id(self):
        # Generate a random message ID (for demonstration purposes)
        return randint(0, 2**64 - 1)

    def __get_next_event_id(self):
        # Generate a random event ID (for demonstration purposes)
        return randint(0, 2**64 - 1)
