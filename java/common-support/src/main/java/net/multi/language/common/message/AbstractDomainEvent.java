package net.multi.language.common.message;

public abstract class AbstractDomainEvent extends AbstractMessage<AbstractMessageType.DomainEventMessage> {

    private final long eventId;

    public AbstractDomainEvent(long messageId, long eventId) {
        super(messageId);
        this.eventId = eventId;
    }

    public long getEventId() {
        return eventId;
    }
}
