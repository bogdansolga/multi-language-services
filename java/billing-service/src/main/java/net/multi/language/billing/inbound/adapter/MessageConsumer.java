package net.multi.language.billing.inbound.adapter;

import net.multi.language.billing.inbound.port.InboundMessagingPort;
import net.multi.language.common.marker.adapter.InboundAdapter;
import net.multi.language.common.message.command.order.ChargeOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MessageConsumer implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private final InboundMessagingPort inboundMessagingPort;

    @Autowired
    public MessageConsumer(final InboundMessagingPort inboundMessagingPort) {
        this.inboundMessagingPort = inboundMessagingPort;
    }

    @Bean
    public Consumer<ChargeOrderCommand> chargeOrder() {
        return chargeOrderCommand -> {
            LOGGER.debug("Received a '{}' command for the order with the ID {} of the customer with the ID {}",
                    chargeOrderCommand.getName(), chargeOrderCommand.getOrderId(), chargeOrderCommand.getCustomerId());

            inboundMessagingPort.chargeOrder(chargeOrderCommand);
        };
    }
}
