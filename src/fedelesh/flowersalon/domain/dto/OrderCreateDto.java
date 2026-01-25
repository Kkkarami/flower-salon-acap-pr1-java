package fedelesh.flowersalon.domain.dto;

import fedelesh.flowersalon.domain.BaseEntity;
import java.util.Map;
import java.util.UUID;

public final class OrderCreateDto extends BaseEntity {

    private final UUID workerId;
    private final Map<UUID, Integer> items;

    public OrderCreateDto(UUID workerId, Map<UUID, Integer> items) {
        super();
        this.workerId = workerId;
        this.items = validatedItems(items);
        ensureNoErrors();
    }

    private Map<UUID, Integer> validatedItems(Map<UUID, Integer> items) {
        if (items == null || items.isEmpty()) {
            addError("items", "Замовлення не може бути порожнім");
        }
        return items;
    }

    private void ensureNoErrors() {
        if (!isValid()) {
            throw new IllegalArgumentException("Помилка замовлення: " + getErrors());
        }
    }

    public UUID workerId() {
        return workerId;
    }

    public Map<UUID, Integer> items() {
        return items;
    }
}
