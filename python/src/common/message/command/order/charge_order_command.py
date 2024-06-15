from enum import Enum
from typing import ClassVar

from common.message.abstract_command import AbstractCommand


class Currency(str, Enum):
    EUR = "EUR"
    USD = "USD"


class ChargeOrderCommand(AbstractCommand):
    NAME: ClassVar[str] = "ChargeOrder"

    customer_id: int
    order_id: int
    order_total: float
    currency: Currency

    def get_name(self) -> str:
        return self.NAME

    def __str__(self) -> str:
        return f"customerId: {self.customer_id}, orderId: {self.order_id}, orderTotal: {self.order_total}, currency: {self.currency}"
