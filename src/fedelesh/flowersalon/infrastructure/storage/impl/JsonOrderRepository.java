package fedelesh.flowersalon.infrastructure.storage.impl;

import com.google.gson.reflect.TypeToken;
import fedelesh.flowersalon.domain.impl.Order;
import fedelesh.flowersalon.infrastructure.storage.JsonFilePath;
import fedelesh.flowersalon.infrastructure.storage.JsonRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.OrderRepository;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

class JsonOrderRepository extends JsonRepository<Order> implements OrderRepository {

    private static final Type LIST_TYPE = new TypeToken<List<Order>>() {
    }.getType();

    public JsonOrderRepository() {
        super(JsonFilePath.ORDERS.getPath(), LIST_TYPE);
    }

    @Override
    public List<Order> findByWorkerId(UUID workerId) {
        return findAllInternal().stream()
              .filter(order -> workerId.equals(order.getWorkerId()))
              .toList();
    }
}
