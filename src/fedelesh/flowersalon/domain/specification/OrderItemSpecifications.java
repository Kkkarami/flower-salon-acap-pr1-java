package fedelesh.flowersalon.domain.specification;

import fedelesh.flowersalon.domain.impl.OrderItem;
import java.util.UUID;

public final class OrderItemSpecifications {

    private OrderItemSpecifications() {
    }

    public static Specification<OrderItem> byOrderId(UUID orderId) {
        return oi -> oi.getOrderId() != null
              && oi.getOrderId().equals(orderId);
    }

    public static Specification<OrderItem> byProductId(UUID productId) {
        return oi -> oi.getProductId() != null
              && oi.getProductId().equals(productId);
    }

    public static Specification<OrderItem> byOrderAndProduct(
          UUID orderId, UUID productId) {
        return byOrderId(orderId).and(byProductId(productId));
    }

    public static Specification<OrderItem> all() {
        return oi -> true;
    }

    public static Specification<OrderItem> none() {
        return oi -> false;
    }
}
