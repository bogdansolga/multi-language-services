from abc import ABC


class AbstractMessageType(ABC):
    pass


class CommandMessage(AbstractMessageType):
    pass


class QueryMessage(AbstractMessageType):
    pass


class DomainEventMessage(AbstractMessageType):
    pass
