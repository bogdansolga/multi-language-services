import json
import logging

from common.marker.message.channel import Channel
from common.message.command.order import CreateOrderCommand

from .services import OrderService

# Configure logging
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


def create_order_consumer(app, message):
    data = json.loads(message.value().decode("utf-8"))
    command = CreateOrderCommand(**data)
    logger.debug(
        f"Received a {command.__class__.__name__} command, the ordered item is '{command.product_name}', the customer ID is {command.customer_id}"
    )
    with app.app_context():
        order_service = OrderService()
        order_service.create_order_messaging(command)


consumer_configs = [
    {
        "name": "create-order-consumer",
        "topics": [Channel.CREATE_ORDER.get_channel_name()],
        "handler": create_order_consumer,
    },
]
