package net.multi.language.order.outbound.port;

import net.multi.language.common.marker.port.OutboundPort;
import net.multi.language.order.domain.model.Order;

public interface OutboundPersistencePort extends OutboundPort {
    long save(Order order);
}
