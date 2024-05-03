package net.multi.language.common.helper;

import net.multi.language.common.message.AbstractMessage;
import net.multi.language.common.message.OutputBindings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

    private final StreamBridge streamBridge;

    @Autowired
    public MessagePublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Async
    public <Message extends AbstractMessage<?>> void sendMessage(OutputBindings binding, Message message) {
        streamBridge.send(binding.getBindingName(), message);
    }
}
