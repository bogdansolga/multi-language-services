package net.multi.language.order.inbound.adapter;

import net.multi.language.order.inbound.port.InboundMessagingPort;
import net.multi.language.common.marker.adapter.InboundAdapter;
import net.multi.language.common.message.command.order.CreateOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MessagingInboundAdapter implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingInboundAdapter.class);

    private final InboundMessagingPort inboundMessagingPort;

    @Autowired
    public MessagingInboundAdapter(final InboundMessagingPort inboundMessagingPort) {
        this.inboundMessagingPort = inboundMessagingPort;
    }

    @Bean
    public Consumer<CreateOrderCommand> createOrder() {
        return createOrderCommand -> {
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            LOGGER.debug("Received a '{}' command, the ordered item is '{}', the customer ID is {}",
                    createOrderCommand.getName(), createOrderCommand.getProductName(), createOrderCommand.getCustomerId());

            inboundMessagingPort.createOrder(createOrderCommand);
        };
    }
}
