from common.helper import env, kafka, scheduler
from flask import Flask
from flask_migrate import Migrate

from .config import Config
from .consumers import consumer_configs
from .controllers import order_blueprint
from .db import db
from .extensions import Extensions
from .schedules import OrdersClient


def run_order_service():
    app = Flask("order_service")
    app.config.from_object(Config)
    env.add_config_to_env(app)

    kafka.create_kafka_topics(app.config["KAFKA_BROKERS"], app.config["KAFKA_TOPICS"])

    app.app_context().push()
    app.register_blueprint(order_blueprint)

    db.init_app(app)
    Migrate(app, db)
    with app.app_context():
        db.create_all()

    Extensions(app)

    orders_client = OrdersClient()
    schedule_configs = [
        {
            "func": lambda: orders_client.publish_order_creation_message(app),
            "trigger": "interval",
            "seconds": 3,
        }
    ]

    scheduler.add_schedules(schedule_configs)

    kafka.run_consumers(app, consumer_configs)

    app.run(debug=True, port=app.config["PORT"])
