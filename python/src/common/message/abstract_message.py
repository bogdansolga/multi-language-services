from abc import ABC, abstractmethod
from datetime import datetime
from typing import Generic, TypeVar

from pydantic import BaseModel, Field

from common.message.abstract_message_type import (
    CommandMessage,
    DomainEventMessage,
    QueryMessage,
)

T = TypeVar(
    "T",
    CommandMessage,
    QueryMessage,
    DomainEventMessage,
)


class AbstractMessage(BaseModel, Generic[T], ABC):
    message_id: int
    creation_date_time: datetime = Field(default_factory=datetime.now)

    @abstractmethod
    def get_name(self) -> str:
        pass

    def get_message_id(self) -> int:
        return self.message_id

    def get_creation_date_time(self) -> datetime:
        return self.creation_date_time

    class Config:
        arbitrary_types_allowed = True
