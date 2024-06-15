from .abstract_message import AbstractMessage
from .abstract_message_type import DomainEventMessage


class AbstractDomainEvent(AbstractMessage[DomainEventMessage]):
    event_id: int

    def get_event_id(self) -> int:
        return self.event_id
