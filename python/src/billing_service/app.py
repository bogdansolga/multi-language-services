from common.helper import env, kafka
from flask import Flask

from .config import Config
from .consumers import consumer_configs
from .controllers import billing_blueprint
from .extensions import Extensions


def run_billing_service():
    app = Flask("billing_service")
    app.config.from_object(Config)
    env.add_config_to_env(app)

    app.app_context().push()

    kafka.create_kafka_topics(app.config["KAFKA_BROKERS"], app.config["KAFKA_TOPICS"])

    app.register_blueprint(billing_blueprint)

    Extensions(app)

    kafka.run_consumers(app, consumer_configs)

    app.run(debug=True, port=app.config["PORT"])
