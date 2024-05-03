package net.multi.language.order.inbound.port;

import net.multi.language.common.marker.port.InboundPort;
import net.multi.language.common.message.command.order.CreateOrderCommand;

public interface InboundMessagingPort extends InboundPort {

    void createOrder(final CreateOrderCommand createOrderCommand);
}
