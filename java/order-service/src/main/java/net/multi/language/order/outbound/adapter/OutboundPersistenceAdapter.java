package net.multi.language.order.outbound.adapter;

import net.multi.language.order.domain.model.Order;
import net.multi.language.order.outbound.port.OutboundPersistencePort;
import net.multi.language.order.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutboundPersistenceAdapter implements OutboundPersistencePort {

    private final OrderRepository orderRepository;

    @Autowired
    public OutboundPersistenceAdapter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(
            readOnly = false,
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED
    )
    public long save(Order order) {
        return orderRepository.save(order)
                              .getId();
    }
}
