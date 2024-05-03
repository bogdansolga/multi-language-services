package net.multi.language.order.service;

import net.multi.language.order.outbound.port.OutboundMessagingPort;
import net.multi.language.common.dto.order.OrderDTO;
import net.multi.language.common.message.command.order.ChargeOrderCommand;
import net.multi.language.common.message.command.order.CreateOrderCommand;
import net.multi.language.common.message.event.order.OrderCreatedEvent;
import net.multi.language.order.domain.model.Order;
import net.multi.language.order.inbound.port.InboundMessagingPort;
import net.multi.language.order.inbound.port.InboundRestPort;
import net.multi.language.order.outbound.port.OutboundPersistencePort;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService implements InboundRestPort, InboundMessagingPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    private static final Random RANDOM = new Random(100);

    private final OutboundMessagingPort outboundMessagingPort;
    private final OutboundPersistencePort outboundPersistencePort;

    @Value("${billing.service.endpoint}")
    private String billingServiceEndpoint;

    @Autowired
    public OrderService(final OutboundMessagingPort outboundMessagingPort,
                        final OutboundPersistencePort outboundPersistencePort) {
        this.outboundMessagingPort = outboundMessagingPort;
        this.outboundPersistencePort = outboundPersistencePort;
    }

    // creating an order received from a REST endpoint (UI, testing app etc)
    @Override
    @Transactional
    public void createOrder(final OrderDTO orderDTO) {
        final long customerId = orderDTO.getCustomerId();
        final long orderId = saveOrder(convertDTOIntoOrder(orderDTO));

        LOGGER.info("Creating an order for the customer with the ID {}, for {} items...", customerId,
                orderDTO.getOrderItems().size());
        final double orderTotal = orderDTO.getOrderTotal();

        CompletableFuture.runAsync(() -> publishChargeOrder(customerId, orderId, orderTotal))
                         .thenRunAsync(() -> publishOrderCreatedEvent(customerId, orderId));
    }

    @Override
    public void chargeOrder(long customerId, long orderId) {
        final RestClient restClient = RestClient.create();
        final ResponseEntity<String> response = chargeOrder(customerId, orderId, restClient);
        LOGGER.info("Got the response: {}", response.getBody());
    }

    private @NotNull ResponseEntity<String> chargeOrder(long customerId, long orderId, RestClient restClient) {
        return restClient.post()
                         .uri(billingServiceEndpoint + "/" + customerId + "/" + orderId)
                         .retrieve()
                         .toEntity(String.class);
    }

    // creating an order received from a messaging endpoint (upstream system, 3rd party application etc)
    @Override
    @Transactional
    public void createOrder(final CreateOrderCommand createOrderCommand) {
        final long customerId = createOrderCommand.getCustomerId();
        LOGGER.info("Creating an order for the product '{}', for the customer with the ID {}...",
                createOrderCommand.getProductName(), customerId);

        final long orderId = saveOrder(convertCommandIntoOrder(createOrderCommand));
        final double orderTotal = createOrderCommand.getOrderTotal();

        publishChargeOrder(customerId, orderId, orderTotal);
        publishOrderCreatedEvent(customerId, orderId);
    }

    private void publishChargeOrder(final long customerId, final long orderId, final double orderTotal) {
        outboundMessagingPort.publishChargeOrderCommand(
                new ChargeOrderCommand(getNextMessageId(), customerId, orderId, orderTotal,
                        ChargeOrderCommand.Currency.EUR));
    }

    private void publishOrderCreatedEvent(final long customerId, final long orderId) {
        outboundMessagingPort.publishOrderCreatedEvent(
                new OrderCreatedEvent(getNextMessageId(), getNextEventId(), customerId, orderId));
    }

    private Order convertCommandIntoOrder(final CreateOrderCommand command) {
        return new Order(command.getCustomerId(), command.getOrderTotal());
    }

    private Order convertDTOIntoOrder(final OrderDTO orderDTO) {
        return new Order(orderDTO.getCustomerId(), orderDTO.getOrderTotal());
    }

    private long saveOrder(final Order order) {
        return outboundPersistencePort.save(order);
    }

    private long getNextMessageId() {
        return RANDOM.nextLong();
    }

    private long getNextEventId() {
        // returned from the saved database event, before sending it (using transactional messaging)
        return RANDOM.nextLong();
    }
}
