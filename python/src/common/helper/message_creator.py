import json
from typing import TypeVar
from uuid import uuid4

from common.message import AbstractMessage

Payload = TypeVar("Payload", bound=AbstractMessage)


class MessageCreator:
    @staticmethod
    def create(payload: Payload) -> dict:
        headers = {
            "Content-Type": "application/json",
            "correlationId": str(uuid4()),
            "version": "1.2",
            "error_channel_"
            + payload.get_name(): "error_channel_"
            + payload.get_name(),
            "reply_channel_"
            + payload.get_name(): "reply_channel_"
            + payload.get_name(),
        }

        return {"payload": json.dumps(payload.__dict__), "headers": headers}
