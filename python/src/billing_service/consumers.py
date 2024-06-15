import json
import logging

from common.marker.message.channel import Channel
from common.message.command.order import ChargeOrderCommand

from .services import BillingService

# Configure logging
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


def charge_order_consumer(app, message):
    decoded_msg = message.value().decode("utf-8")
    command = ChargeOrderCommand.parse_raw(decoded_msg)
    logger.debug(
        f"Received a '{command.__class__.__name__}' command for the order with the ID {command.order_id} of the customer with the ID {command.customer_id}"
    )

    with app.app_context():
        billing_service = BillingService()
        billing_service.charge_order(command)

consumer_configs = [
    {
        "name": "charge-order-consumer",
        "topics": [Channel.CHARGE_ORDER.get_channel_name()],
        "handler": charge_order_consumer,
    },
]
