class Channels:
    class Commands:
        CHARGE_ORDER = "charge_order"
        CREATE_ORDER = "create_order"

    class Events:
        ORDER_CREATED = "order_created"
        ORDER_CHARGED = "order_charged"
        ORDER_NOT_CHARGED = "order_not_charged"

    class Queries:
        FIND_ORDER = "find_order"

    def __new__(cls):
        raise TypeError("Channels is not instantiable")
