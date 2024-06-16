from typing import List

from pydantic import BaseModel

from .order_item_dto import OrderItemDTO


class OrderDTO(BaseModel):
    customer_id: int
    order_id: int
    order_items: List[OrderItemDTO]

    def get_order_total(self) -> float:
        return sum(item.product_price for item in self.order_items)
