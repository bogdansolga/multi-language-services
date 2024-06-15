from confluent_kafka import Producer
from pydantic import BaseModel


class MessagePublisher:
    def __init__(self, bootstrap_servers: str):
        self.producer = Producer({"bootstrap.servers": bootstrap_servers})

    def send_message(self, topic: str, message: BaseModel):
        # Serialize message to JSON
        json_message = message.model_dump_json()

        # Produce message to Kafka topic
        self.producer.produce(topic, json_message.encode("utf-8"))

        # Flush messages to ensure they are sent
        self.producer.flush()
