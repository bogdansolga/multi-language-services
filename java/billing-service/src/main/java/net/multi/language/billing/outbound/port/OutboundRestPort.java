package net.multi.language.billing.outbound.port;

import net.multi.language.common.dto.order.OrderChargingStatusDTO;
import net.multi.language.common.marker.port.OutboundPort;

public interface OutboundRestPort extends OutboundPort {

    OrderChargingStatusDTO charge(int paymentMethodId, double amount);
}
