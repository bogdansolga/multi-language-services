package net.multi.language.billing.inbound.port;

import net.multi.language.common.dto.order.PaymentDTO;
import net.multi.language.common.marker.port.InboundPort;

import java.util.List;

public interface InboundRestPort extends InboundPort {

    String chargeCustomer(long customerId, long orderId);

    List<PaymentDTO> getPaymentsForCustomer(long customerId);
}
