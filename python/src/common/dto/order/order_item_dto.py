from pydantic import BaseModel


class OrderItemDTO(BaseModel):
    order_item_id: int
    product_name: str
    product_price: float

    class Config:
        from_attributes = True
