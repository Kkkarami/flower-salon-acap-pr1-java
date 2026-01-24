package fedelesh.flowersalon.infrastructure.storage.contract;

import fedelesh.flowersalon.domain.impl.OrderItem;
import fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends Repository<OrderItem> {

    /**
     * Отримати всі позиції конкретного замовлення.
     */
    List<OrderItem> findByOrderId(UUID orderId);

    /**
     * Очистити склад замовлення.
     */
    void deleteByOrderId(UUID orderId);

    /**
     * Перевірити, чи продавався товар хоча б раз (перед видаленням товару).
     */
    boolean existsByProductId(UUID productId);
}
