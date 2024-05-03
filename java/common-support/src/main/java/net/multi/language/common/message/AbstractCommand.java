package net.multi.language.common.message;

public abstract class AbstractCommand extends AbstractMessage<AbstractMessageType.CommandMessage> {

    public AbstractCommand(long messageId) {
        super(messageId);
    }
}
