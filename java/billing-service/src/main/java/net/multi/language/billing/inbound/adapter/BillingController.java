package net.multi.language.billing.inbound.adapter;

import net.multi.language.billing.inbound.port.InboundRestPort;
import net.multi.language.common.dto.order.PaymentDTO;
import net.multi.language.common.marker.adapter.InboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController implements InboundAdapter {

    private final InboundRestPort inboundRestPort;

    @Autowired
    public BillingController(final InboundRestPort inboundRestPort) {
        this.inboundRestPort = inboundRestPort;
    }

    @PostMapping("/{customerId}/{orderId}")
    public ResponseEntity<String> chargeCustomer(@PathVariable long customerId, @PathVariable long orderId) {
        final String result = inboundRestPort.chargeCustomer(customerId, orderId);
        return result.contains("not") ? ResponseEntity.badRequest().body(result) : ResponseEntity.ok(result);
    }

    @GetMapping("/{customerId}")
    public List<PaymentDTO> getPaymentsForCustomer(@PathVariable final long customerId) {
        return inboundRestPort.getPaymentsForCustomer(customerId);
    }
}
