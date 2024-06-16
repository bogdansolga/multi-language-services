from typing import ClassVar

from common.message.abstract_command import AbstractCommand


class CreateOrderCommand(AbstractCommand):
    NAME: ClassVar[str] = "CreateOrder"

    customer_id: int
    product_name: str
    order_total: float

    def get_name(self) -> str:
        return self.NAME

    def __eq__(self, other) -> bool:
        if not isinstance(other, CreateOrderCommand):
            return False
        return (
            self.customer_id == other.customer_id
            and self.product_name == other.product_name
            and self.order_total == other.order_total
        )

    def __hash__(self) -> int:
        return hash((self.product_name, self.order_total, self.customer_id))
