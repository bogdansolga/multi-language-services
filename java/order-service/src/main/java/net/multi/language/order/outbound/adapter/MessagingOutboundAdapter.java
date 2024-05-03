package net.multi.language.order.outbound.adapter;

import net.multi.language.order.outbound.port.OutboundMessagingPort;
import net.multi.language.common.helper.MessagePublisher;
import net.multi.language.common.marker.adapter.OutboundAdapter;
import net.multi.language.common.message.OutputBindings;
import net.multi.language.common.message.event.order.OrderCreatedEvent;
import net.multi.language.common.message.command.order.ChargeOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagingOutboundAdapter implements OutboundMessagingPort, OutboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingOutboundAdapter.class);

    private final MessagePublisher messagePublisher;

    @Autowired
    public MessagingOutboundAdapter(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void publishOrderCreatedEvent(final OrderCreatedEvent orderCreatedEvent) {
        //TODO find a way to directly use the orderCreatedProducer
        messagePublisher.sendMessage(OutputBindings.ORDER_CREATED, orderCreatedEvent);
        LOGGER.info("The OrderCreatedEvent '{}' was published", orderCreatedEvent);
    }

    @Override
    public void publishChargeOrderCommand(final ChargeOrderCommand chargeOrderCommand) {
        //TODO find a way to directly use the chargeOrderProducer
        messagePublisher.sendMessage(OutputBindings.CHARGE_ORDER, chargeOrderCommand);
        LOGGER.info("The ChargeOrderCommand '{}' was published", chargeOrderCommand);
    }
}
