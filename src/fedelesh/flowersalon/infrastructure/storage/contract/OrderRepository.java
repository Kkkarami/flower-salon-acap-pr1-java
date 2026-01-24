package fedelesh.flowersalon.infrastructure.storage.contract;

import fedelesh.flowersalon.domain.impl.Order;
import fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends Repository<Order> {

    /**
     * Отримати всі замовлення, оформлені конкретним працівником.
     */
    List<Order> findByWorkerId(UUID workerId);
}