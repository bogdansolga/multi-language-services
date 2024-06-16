from enum import Enum


class OrderStatus(Enum):
    CREATED = "CREATED"
    PAYED = "PAYED"
    IN_PROCESSING = "IN_PROCESSING"
    DELIVERED = "DELIVERED"
