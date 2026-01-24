package fedelesh.flowersalon.infrastructure.storage.impl;

import com.google.gson.reflect.TypeToken;
import fedelesh.flowersalon.domain.impl.OrderItem;
import fedelesh.flowersalon.infrastructure.storage.JsonFilePath;
import fedelesh.flowersalon.infrastructure.storage.JsonRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.OrderItemRepository;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

class JsonOrderItemRepository extends JsonRepository<OrderItem> implements OrderItemRepository {

    private static final Type LIST_TYPE = new TypeToken<List<OrderItem>>() {
    }.getType();

    public JsonOrderItemRepository() {
        super(JsonFilePath.ORDER_ITEM.getPath(), LIST_TYPE);
    }

    @Override
    public List<OrderItem> findByOrderId(UUID orderId) {
        return findAllInternal().stream()
              .filter(item -> orderId.equals(item.getOrderId()))
              .toList();
    }

    @Override
    public void deleteByOrderId(UUID orderId) {
        List<OrderItem> allItems = findAllInternal();
        List<OrderItem> toKeep = allItems.stream()
              .filter(item -> !orderId.equals(item.getOrderId()))
              .toList();

        if (toKeep.size() < allItems.size()) {
            invalidateCache();
            writeToFile(toKeep);
        }
    }

    @Override
    public boolean existsByProductId(UUID productId) {
        return findAllInternal().stream()
              .anyMatch(item -> productId.equals(item.getProductId()));
    }
}
