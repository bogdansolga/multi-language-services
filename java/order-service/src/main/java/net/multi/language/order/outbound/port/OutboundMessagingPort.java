package net.multi.language.order.outbound.port;

import net.multi.language.common.marker.port.OutboundPort;
import net.multi.language.common.message.command.order.ChargeOrderCommand;
import net.multi.language.common.message.event.order.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface OutboundMessagingPort extends OutboundPort {
    void publishOrderCreatedEvent(final OrderCreatedEvent orderCreatedEvent);

    void publishChargeOrderCommand(final ChargeOrderCommand chargeOrderCommand);
}
