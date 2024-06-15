from enum import Enum

from .channels import Channels


class Channel(Enum):
    CHARGE_ORDER = Channels.Commands.CHARGE_ORDER
    CREATE_ORDER = Channels.Commands.CREATE_ORDER
    ORDER_CREATED = Channels.Events.ORDER_CREATED
    ORDER_CHARGED = Channels.Events.ORDER_CHARGED
    ORDER_NOT_CHARGED = Channels.Events.ORDER_NOT_CHARGED

    def __init__(self, channel_name: str):
        self.channel_name = channel_name

    def get_channel_name(self) -> str:
        return self.channel_name
