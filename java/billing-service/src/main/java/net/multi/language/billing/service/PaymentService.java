package net.multi.language.billing.service;

import net.multi.language.billing.outbound.port.OutboundRestPort;
import net.multi.language.common.dto.order.OrderChargingStatusDTO;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements OutboundRestPort {

    // invokes a payment gateway (ex: Braintree) and returns the charging result
    @Override
    @SuppressWarnings("unused")
    public synchronized OrderChargingStatusDTO charge(final int paymentMethodId, final double amount) {
        return System.currentTimeMillis() % 2 == 0 ? new OrderChargingStatusDTO() :
                new OrderChargingStatusDTO(false, "The card has expired");
    }
}
