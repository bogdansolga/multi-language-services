package net.multi.language.billing.config;

import net.multi.language.common.helper.MessageCreator;
import net.multi.language.common.message.event.order.OrderChargedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Function;

@Configuration
public class ProducersConfig {

    @Bean
    public Function<OrderChargedEvent, Message<OrderChargedEvent>> orderChargedProducer() {
        return MessageCreator::create;
    }
}
