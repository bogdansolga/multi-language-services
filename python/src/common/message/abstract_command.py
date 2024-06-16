from .abstract_message import AbstractMessage
from .abstract_message_type import CommandMessage


class AbstractCommand(AbstractMessage[CommandMessage]):
    pass
