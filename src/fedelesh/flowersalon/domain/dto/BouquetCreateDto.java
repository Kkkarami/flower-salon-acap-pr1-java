package fedelesh.flowersalon.domain.dto;

import fedelesh.flowersalon.domain.BaseEntity;
import java.util.Map;
import java.util.UUID;

public final class BouquetCreateDto extends BaseEntity {

    private final String name;
    private final Map<UUID, Integer> flowerIdsWithQuantities;
    private final UUID wrapperId;

    public BouquetCreateDto(String name, Map<UUID, Integer> flowerIdsWithQuantities,
          UUID wrapperId) {
        super();
        this.name = validatedName(name);
        this.flowerIdsWithQuantities = validatedFlowers(flowerIdsWithQuantities);
        this.wrapperId = wrapperId; // Упаковка може бути null, якщо букет без неї

        ensureNoErrors();
    }

    private String validatedName(String name) {
        if (name == null || name.isBlank()) {
            addError("name", "Назва букета не може бути порожньою");
        }
        return name;
    }

    private Map<UUID, Integer> validatedFlowers(Map<UUID, Integer> flowers) {
        if (flowers == null || flowers.isEmpty()) {
            addError("flowers", "Букет повинен містити хоча б одну квітку");
        }
        return flowers;
    }

    private void ensureNoErrors() {
        if (!isValid()) {
            throw new IllegalArgumentException("Помилка створення букета: " + getErrors());
        }
    }

    public String name() {
        return name;
    }

    public Map<UUID, Integer> flowerIdsWithQuantities() {
        return flowerIdsWithQuantities;
    }

    public UUID wrapperId() {
        return wrapperId;
    }
}
