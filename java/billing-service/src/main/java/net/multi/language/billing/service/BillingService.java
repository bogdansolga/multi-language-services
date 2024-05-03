package net.multi.language.billing.service;

import net.multi.language.billing.inbound.port.InboundMessagingPort;
import net.multi.language.billing.outbound.port.OutboundMessagingPort;
import net.multi.language.billing.inbound.port.InboundRestPort;
import net.multi.language.billing.outbound.port.OutboundRestPort;
import net.multi.language.common.dto.order.OrderChargingStatusDTO;
import net.multi.language.common.dto.order.PaymentDTO;
import net.multi.language.common.message.command.order.ChargeOrderCommand;
import net.multi.language.common.message.event.order.OrderChargedEvent;
import net.multi.language.common.message.event.order.OrderNotChargedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BillingService implements InboundRestPort, InboundMessagingPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingService.class);
    private static final Random RANDOM = new Random(100);

    private final OutboundMessagingPort outboundMessagingPort;
    private final OutboundRestPort outboundRestPort;

    @Autowired
    public BillingService(final OutboundMessagingPort outboundMessagingPort, final OutboundRestPort outboundRestPort) {
        this.outboundMessagingPort = outboundMessagingPort;
        this.outboundRestPort = outboundRestPort;
    }

    @Override
    @Transactional
    public void chargeOrder(final ChargeOrderCommand chargeOrderCommand) {
        final long customerId = chargeOrderCommand.getCustomerId();
        final long orderId = chargeOrderCommand.getOrderId();
        final double orderTotal = chargeOrderCommand.getOrderTotal();

        LOGGER.info("Charging the customer with the ID {}, for the order with Id {}, for {} {}...", customerId, orderId,
                orderTotal, chargeOrderCommand.getCurrency());

        final int usedPaymentMethod = getPaymentMethod();
        final OrderChargingStatusDTO orderChargingStatus = outboundRestPort.charge(usedPaymentMethod, orderTotal);

        if (orderChargingStatus.isSuccessful()) {
            LOGGER.info("The customer {} was successfully charged for the order {}", customerId, orderId);
            outboundMessagingPort.publishOrderChargedEvent(createOrderChargedEvent(customerId, orderId));
        } else {
            final String failureReason = orderChargingStatus.getFailureReason()
                                                            .orElse("Cannot charge the card");
            LOGGER.warn("The customer {} could not be charged for the order {} - '{}'", customerId, orderId, failureReason);
            outboundMessagingPort.publishOrderNotChargedEvent(createOrderNotChargedEvent(customerId, orderId, failureReason));
        }
    }

    private OrderChargedEvent createOrderChargedEvent(long customerId, long orderId) {
        return new OrderChargedEvent(getNextMessageId(), getNextEventId(), customerId, orderId);
    }

    private OrderNotChargedEvent createOrderNotChargedEvent(long customerId, long orderId, String failureReason) {
        return new OrderNotChargedEvent(getNextMessageId(), getNextEventId(), customerId, orderId, failureReason);
    }

    @Override
    @Transactional
    public List<PaymentDTO> getPaymentsForCustomer(long customerId) {
        return new ArrayList<>();
    }

    private int getPaymentMethod() {
        // return the user's payment methods
        return RANDOM.nextInt(20);
    }

    private long getNextMessageId() {
        return RANDOM.nextLong();
    }

    private long getNextEventId() {
        // returned from the saved database event, before sending it (using transactional messaging)
        return RANDOM.nextLong();
    }
}
