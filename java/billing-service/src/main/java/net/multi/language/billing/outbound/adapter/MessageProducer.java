package net.multi.language.billing.outbound.adapter;

import net.multi.language.billing.outbound.port.OutboundMessagingPort;
import net.multi.language.common.helper.MessagePublisher;
import net.multi.language.common.message.OutputBindings;
import net.multi.language.common.message.event.order.OrderChargedEvent;
import net.multi.language.common.message.event.order.OrderNotChargedEvent;
import net.multi.language.common.marker.adapter.OutboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements OutboundMessagingPort, OutboundAdapter {

    private final MessagePublisher messagePublisher;

    @Autowired
    public MessageProducer(final MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void publishOrderChargedEvent(final OrderChargedEvent orderChargedEvent) {
        messagePublisher.sendMessage(OutputBindings.ORDER_CHARGED, orderChargedEvent);
    }

    @Override
    public void publishOrderNotChargedEvent(final OrderNotChargedEvent orderNotChargedEvent) {
    }
}
