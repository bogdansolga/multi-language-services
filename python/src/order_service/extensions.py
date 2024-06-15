from flask import Flask

from .producer import Producer
from .services import OrderService


class Extensions:
    def __init__(self, app=None):
        self.billing_service = None
        self.payment_service = None
        if app is not None:
            self.init_app(app)

    def init_app(self, app: Flask):
        app.extensions["producer"] = Producer(app.config["KAFKA_BROKERS"])
        app.extensions["order_service"] = OrderService()
