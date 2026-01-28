package fedelesh.flowersalon.infrastructure.storage.impl;

import com.google.gson.reflect.TypeToken;
import fedelesh.flowersalon.domain.impl.OrderItem;
import fedelesh.flowersalon.infrastructure.storage.JsonFilePath;
import fedelesh.flowersalon.infrastructure.storage.JsonRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.OrderItemRepository;
import java.lang.reflect.Type;
import java.util.List;

class JsonOrderItemRepository extends JsonRepository<OrderItem> implements OrderItemRepository {

    private static final Type LIST_TYPE = new TypeToken<List<OrderItem>>() {
    }.getType();

    public JsonOrderItemRepository() {
        super(JsonFilePath.ORDER_ITEM.getPath(), LIST_TYPE);
    }
}
