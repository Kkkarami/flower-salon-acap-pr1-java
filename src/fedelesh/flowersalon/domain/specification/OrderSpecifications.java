package fedelesh.flowersalon.domain.specification;

import fedelesh.flowersalon.domain.enums.OrderStatus;
import fedelesh.flowersalon.domain.impl.Order;
import java.time.LocalDateTime;
import java.util.UUID;

public final class OrderSpecifications {

    private OrderSpecifications() {
    }

    public static Specification<Order> byStatus(OrderStatus status) {
        return o -> o.getStatus() == status;
    }

    public static Specification<Order> byWorker(UUID workerId) {
        return o -> o.getWorkerId() != null
              && o.getWorkerId().equals(workerId);
    }

    public static Specification<Order> after(LocalDateTime date) {
        return o -> o.getDate() != null
              && o.getDate().isAfter(date);
    }

    public static Specification<Order> before(LocalDateTime date) {
        return o -> o.getDate() != null
              && o.getDate().isBefore(date);
    }

    public static Specification<Order> all() {
        return o -> true;
    }

    public static Specification<Order> none() {
        return o -> false;
    }
}
