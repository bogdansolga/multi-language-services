package net.multi.language.order.config;

import net.multi.language.common.helper.MessageCreator;
import net.multi.language.common.message.command.order.ChargeOrderCommand;
import net.multi.language.common.message.event.order.OrderCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Function;

@Configuration
public class ProducersConfig {

    @Bean
    public Function<OrderCreatedEvent, Message<OrderCreatedEvent>> orderCreatedProducer() {
        return MessageCreator::create;
    }

    @Bean
    public Function<ChargeOrderCommand, Message<ChargeOrderCommand>> chargeOrderProducer() {
        return MessageCreator::create;
    }
}
