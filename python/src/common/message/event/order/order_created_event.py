from typing import ClassVar

from common.message.abstract_domain_event import AbstractDomainEvent


class OrderCreatedEvent(AbstractDomainEvent):
    NAME: ClassVar[str] = "OrderCreated"

    customer_id: int
    order_id: int

    def get_name(self) -> str:
        return self.NAME

    def __str__(self) -> str:
        return f"customerId: {self.customer_id}, orderId: {self.order_id}"
