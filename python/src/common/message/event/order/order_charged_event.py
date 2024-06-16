from typing import ClassVar

from common.message.abstract_domain_event import AbstractDomainEvent


class OrderChargedEvent(AbstractDomainEvent):
    NAME: ClassVar[str] = "OrderCharged"

    customer_id: int
    order_id: int

    def get_name(self) -> str:
        return self.NAME

    def __eq__(self, other) -> bool:
        if not isinstance(other, OrderChargedEvent):
            return False
        return self.customer_id == other.customer_id and self.order_id == other.order_id

    def __hash__(self) -> int:
        return hash((self.customer_id, self.order_id))
