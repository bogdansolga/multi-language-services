package net.multi.language.common.message.command.order;

import net.multi.language.common.marker.message.Channel;
import net.multi.language.common.marker.message.MessageDetails;
import net.multi.language.common.marker.message.Service;
import net.multi.language.common.message.AbstractCommand;

import java.util.Objects;

@MessageDetails(
        publisher = Service.ORDER_SERVICE,
        subscribers = Service.ORDER_SERVICE,
        channel = Channel.CREATE_ORDER
)
public class CreateOrderCommand extends AbstractCommand {

    private static final String NAME = "CreateOrder";

    private final String productName;
    private final double orderTotal;
    private final long customerId;

    public CreateOrderCommand(final long customerId, final long messageId, final String productName, final double orderTotal) {
        super(messageId);
        this.customerId = customerId;
        this.productName = productName;
        this.orderTotal = orderTotal;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getProductName() {
        return productName;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrderCommand that = (CreateOrderCommand) o;
        return Double.compare(that.orderTotal, orderTotal) == 0 &&
                customerId == that.customerId &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, orderTotal, customerId);
    }
}
