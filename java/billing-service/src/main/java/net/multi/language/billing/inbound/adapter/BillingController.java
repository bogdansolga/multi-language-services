package net.multi.language.billing.inbound.adapter;

import net.multi.language.billing.inbound.port.InboundRestPort;
import net.multi.language.common.dto.order.PaymentDTO;
import net.multi.language.common.marker.adapter.InboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/{customerId}")
    public List<PaymentDTO> getPaymentsForCustomer(@PathVariable final long customerId) {
        return inboundRestPort.getPaymentsForCustomer(customerId);
    }
}
