package net.multi.language.order.inbound.adapter;

import net.multi.language.order.inbound.port.InboundRestPort;
import net.multi.language.common.dto.order.OrderDTO;
import net.multi.language.common.marker.adapter.InboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/order")
public class OrdersController implements InboundAdapter {

    private final InboundRestPort inboundRestPort;

    @Autowired
    public OrdersController(final InboundRestPort inboundRestPort) {
        this.inboundRestPort = inboundRestPort;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody final OrderDTO orderDTO) {
        inboundRestPort.createOrder(orderDTO);

        return ResponseEntity.ok("The order was successfully created");
    }

    @GetMapping("/{customerId}/{orderId}")
    public ResponseEntity<String> chargeOrder(@PathVariable long customerId, @PathVariable long orderId) {
        inboundRestPort.chargeOrder(customerId, orderId);

        return ResponseEntity.ok("The order request was successfully performed");
    }
}
