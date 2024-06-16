import json
import logging
import threading
from typing import List

from confluent_kafka import Consumer, KafkaError, KafkaException
from confluent_kafka.admin import AdminClient, NewTopic

# Configure logging
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


def create_kafka_topics(brokers: str, topics: List[str]):
    admin_client = AdminClient({"bootstrap.servers": brokers})

    # Create NewTopic objects for each topic
    new_topics = [
        NewTopic(topic, num_partitions=1, replication_factor=1) for topic in topics
    ]

    # Create topics
    fs = admin_client.create_topics(new_topics)

    # Wait for topics to be created (optional)
    for topic, f in fs.items():
        try:
            f.result()  # Raise an exception if the topic creation failed
            print(f"Topic {topic} created successfully")
        except KafkaException as e:
            # If the error is due to the topic already existing, log a message and continue
            if e.args[0].code() == KafkaError.TOPIC_ALREADY_EXISTS:
                # print(f"Topic '{topic}' already exists.")
                pass
            else:
                # For other errors, re-raise the exception
                raise


def create_consumer(app, name, topics, handler):
    # Kafka consumer configuration
    conf = {
        "bootstrap.servers": "localhost:9092",
        "group.id": name,
        "auto.offset.reset": "earliest",
    }

    consumer = Consumer(conf)

    try:
        consumer.subscribe(topics)

        while True:
            msg = consumer.poll(timeout=1.0)
            if msg is None:
                continue
            if msg.error():
                if msg.error().code() == KafkaError._PARTITION_EOF:
                    continue
                else:
                    logger.error(msg.error())
                    break

            try:
                handler(app, msg)
            except json.JSONDecodeError as e:
                logger.error(f"Failed to decode JSON: {e}")

    finally:
        consumer.close()


def run_consumers(app, consumers):
    """
    Run multiple consumer functions in separate threads.

    Args:
        consumer_functions (list): A list of consumer functions to be executed.
    """
    threads = []

    for con in consumers:
        thread = threading.Thread(target=lambda: create_consumer(app, **con))
        threads.append(thread)
        thread.start()

    # Wait for all threads to complete
    for thread in threads:
        thread.join()
