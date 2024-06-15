import logging

from common.helper import MessagePublisher
from common.message.command.order import ChargeOrderCommand, CreateOrderCommand
from common.message.event.order import OrderCreatedEvent
from common.marker.message.channel import Channel


class Producer:

    def __init__(self, brokers):
        self.publisher = MessagePublisher(brokers)
        self.logger = logging.getLogger(__name__)

    def publish_create_order_command(self, command: CreateOrderCommand):
        self.publisher.send_message(Channel.CREATE_ORDER.get_channel_name(), command)
        print(f"The {command.__class__.__name__} '{command}' was published")

    def publish_order_created_event(self, event: OrderCreatedEvent):
        self.publisher.send_message(Channel.ORDER_CREATED.get_channel_name(), event)
        print(f"The {event.__class__.__name__} '{event}' was published")

    def publish_charge_order_command(self, command: ChargeOrderCommand):
        self.publisher.send_message(Channel.CHARGE_ORDER.get_channel_name(), command)
        print(f"The {command.__class__.__name__} '{command}' was published")

