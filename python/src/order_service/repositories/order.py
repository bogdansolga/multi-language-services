from order_service.db import db
from order_service.models.order import Order


class OrderRepository:
    def save(self, order):
        db.session.add(order)
        db.session.commit()
        return order.id

    def find_by_id(self, order_id):
        """Find an order by its ID."""
        return Order.query.get(order_id)

    def find_all(self):
        """Find all orders."""
        return Order.query.all()

    def update(self, order_id, updated_order):
        """Update an existing order."""
        order = self.find_by_id(order_id)
        if not order:
            return None
        for key, value in updated_order.items():
            setattr(order, key, value)
        db.session.commit()
        return order

    def delete(self, order_id):
        """Delete an order by its ID."""
        order = self.find_by_id(order_id)
        if not order:
            return None
        db.session.delete(order)
        db.session.commit()
        return order

    def delete_all(self):
        """Delete all orders."""
        Order.query.delete()
        db.session.commit()
