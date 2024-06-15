from common.helper import MessagePublisher
from common.message.output_bindings import OutputBindings


class Producer:

    def __init__(self, brokers):
        self.publisher = MessagePublisher(brokers)

    def publish_order_charged_event(self, message):
        self.publisher.send_message(OutputBindings.ORDER_CHARGED, message)

    def publish_order_not_charged_event(self, message):
        pass
