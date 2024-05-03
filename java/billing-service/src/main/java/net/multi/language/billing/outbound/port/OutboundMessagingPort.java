package net.multi.language.billing.outbound.port;

import net.multi.language.common.message.event.order.OrderChargedEvent;
import net.multi.language.common.message.event.order.OrderNotChargedEvent;
import net.multi.language.common.marker.port.OutboundPort;
import org.springframework.stereotype.Component;

@Component
public interface OutboundMessagingPort extends OutboundPort {
    void publishOrderChargedEvent(final OrderChargedEvent orderChargedEvent);

    void publishOrderNotChargedEvent(final OrderNotChargedEvent orderNotChargedEvent);
}
