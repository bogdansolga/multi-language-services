from common.marker.message.channels import Channels


class OutputBindings:
    ORDER_CREATED = Channels.Events.ORDER_CREATED
    ORDER_CHARGED = Channels.Events.ORDER_CHARGED
    CHARGE_ORDER = Channels.Commands.CHARGE_ORDER
