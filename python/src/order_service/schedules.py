from common.message.command.order import CreateOrderCommand
from .producer import Producer

class OrdersClient:
    def __init__(self):
        self.customer_id_generator = 243
        self.order_amount_generator = 200

    def order_producer(self):
        self.customer_id_generator += 5
        self.order_amount_generator += 125
        return CreateOrderCommand(
            customer_id=self.customer_id_generator,
            message_id=8234,
            product_name="An useful tablet",
            order_total=self.order_amount_generator,
        )

    def publish_order_creation_message(self, app):
        with app.app_context():
            order = self.order_producer()
            producer = Producer(app.config["KAFKA_BROKERS"])
            producer.publish_create_order_command(order)
