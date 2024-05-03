package net.multi.language.common.message.event.order;

import net.multi.language.common.marker.message.Channel;
import net.multi.language.common.marker.message.MessageDetails;
import net.multi.language.common.marker.message.Service;
import net.multi.language.common.message.AbstractDomainEvent;

import java.util.Objects;

@MessageDetails(
        publisher = Service.BILLING_SERVICE,
        subscribers = Service.ORDER_SERVICE,
        channel = Channel.ORDER_NOT_CHARGED
)
public class OrderNotChargedEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderNotCharged";

    private final long customerId;
    private final long orderId;
    private final String reason;

    public OrderNotChargedEvent(final long messageId, final long eventId, final long customerId, long orderId,
                                final String reason) {
        super(messageId, eventId);
        this.customerId = customerId;
        this.orderId = orderId;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderNotChargedEvent that = (OrderNotChargedEvent) o;
        return customerId == that.customerId &&
                orderId == that.orderId &&
                Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, orderId, reason);
    }
}
