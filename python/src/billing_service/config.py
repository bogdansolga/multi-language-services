class Config(object):
    DEBUG = True
    DEVELOPMENT = True
    PORT = 8081
    KAFKA_BROKERS = "localhost:9092"
    KAFKA_TOPICS = [
        "order_charged",
        "order_not_charged",
    ]
