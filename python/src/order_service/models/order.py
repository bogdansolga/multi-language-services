from sqlalchemy import Column, DateTime, Enum, ForeignKey, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship

from .order_status import OrderStatus

Base = declarative_base()


class Order(Base):
    __tablename__ = "orders"

    id = Column(Integer, primary_key=True)
    customer_id = Column(Integer)
    total = Column(Integer)
    status = Column(
        Enum(OrderStatus, name="order_status"),
    )
    creation_date = Column(DateTime)

    order_items = relationship(
        "OrderItem",
        lazy="select",  # Lazy loading
        cascade="all, delete-orphan",  # Cascade behavior
        backref="order",  # Mapped by "order" attribute in OrderItem
        order_by="OrderItem.id.asc()",  # Order by clause
    )


class OrderItem(Base):
    __tablename__ = "order_items"

    id = Column(Integer, primary_key=True)
    restaurant_id = Column(Integer)
    food_id = Column(Integer)
    price = Column(Integer)
    name = Column(String)
    description = Column(String)

    order_id = Column(Integer, ForeignKey("orders.id"))
