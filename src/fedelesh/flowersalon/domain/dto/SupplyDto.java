package fedelesh.flowersalon.domain.dto;

import fedelesh.flowersalon.domain.BaseEntity;
import java.util.UUID;

public final class SupplyDto extends BaseEntity {

    private final UUID supplierId;
    private final UUID flowerId;
    private final int quantity;

    public SupplyDto(UUID supplierId, UUID flowerId, int quantity) {
        super();
        this.supplierId = supplierId;
        this.flowerId = flowerId;
        this.quantity = validatedQuantity(quantity);
        ensureNoErrors();
    }

    private int validatedQuantity(int qty) {
        if (qty <= 0) {
            addError("quantity", "Кількість у поставці має бути більше 0");
        }
        return qty;
    }

    private void ensureNoErrors() {
        if (!isValid()) {
            throw new IllegalArgumentException("Помилка поставки: " + getErrors());
        }
    }

    public UUID supplierId() {
        return supplierId;
    }

    public UUID flowerId() {
        return flowerId;
    }

    public int quantity() {
        return quantity;
    }
}
