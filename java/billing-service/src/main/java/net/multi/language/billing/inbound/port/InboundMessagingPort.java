package net.multi.language.billing.inbound.port;

import net.multi.language.common.marker.port.InboundPort;
import net.multi.language.common.message.command.order.ChargeOrderCommand;

public interface InboundMessagingPort extends InboundPort {

    void chargeOrder(final ChargeOrderCommand chargeOrderCommand);
}
