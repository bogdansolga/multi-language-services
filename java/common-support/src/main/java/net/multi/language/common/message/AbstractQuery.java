package net.multi.language.common.message;

public abstract class AbstractQuery extends AbstractMessage<AbstractMessageType.QueryMessage> {

    public AbstractQuery(long messageId) {
        super(messageId);
    }
}
