package net.multi.language.common.message.event.order;

import net.multi.language.common.marker.message.Channel;
import net.multi.language.common.marker.message.MessageDetails;
import net.multi.language.common.marker.message.Service;
import net.multi.language.common.message.AbstractDomainEvent;

@MessageDetails(
        publisher = Service.ORDER_SERVICE,
        subscribers = Service.BILLING_SERVICE,
        channel = Channel.ORDER_CREATED
)
public class OrderCreatedEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderCreated";

    private final long customerId;
    private final long orderId;

    public OrderCreatedEvent(final long messageId, final long eventId, final long customerId, final long orderId) {
        super(messageId, eventId);
        this.customerId = customerId;
        this.orderId = orderId;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "customerId: " + customerId +
                ", orderId: " + orderId +
                '}';
    }
}
