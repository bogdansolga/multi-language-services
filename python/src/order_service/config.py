class Config(object):
    DEBUG = True
    DEVELOPMENT = True
    PORT = 8080

    POSTGRES_USER = "orders_admin"
    POSTGRES_PW = "orders_pass"
    POSTGRES_DB = "orders"
    POSTGRES_HOST = "localhost"
    POSTGRES_PORT = "5432"
    SQLALCHEMY_DATABASE_URI = f"postgresql://{POSTGRES_USER}:{POSTGRES_PW}@{POSTGRES_HOST}:{POSTGRES_PORT}/{POSTGRES_DB}"
    SQLALCHEMY_TRACK_MODIFICATIONS = False

    BILLING_SERVICE_ENDPOINT = "http://localhost:8081/billing"
    KAFKA_BROKERS = "localhost:9092"
    KAFKA_TOPICS = [
        "create_order",
        "charge_order",
        "order_created",
    ]
