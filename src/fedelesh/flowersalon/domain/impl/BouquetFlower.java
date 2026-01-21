package fedelesh.flowersalon.domain.impl;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.exception.EntityValidationException;
import fedelesh.flowersalon.domain.util.ValidationError;
import java.util.UUID;

public class BouquetFlower extends BaseEntity {

    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_PRICE = "priceAtCreation";

    private UUID bouquetId;
    private UUID flowerId;
    private int quantity;
    private double priceAtCreation;

    private BouquetFlower() {
        super();
        this.bouquetId = null;
        this.flowerId = null;
    }

    public BouquetFlower(UUID bouquetId, UUID flowerId,
          int quantity, double priceAtCreation) {
        this();

        this.bouquetId = bouquetId;
        this.flowerId = flowerId;

        setQuantity(quantity);
        setPriceAtCreation(priceAtCreation);

        if (!isValid()) {
            throw new EntityValidationException(getErrors());
        }
    }

    public UUID getBouquetId() {
        return bouquetId;
    }

    public UUID getFlowerId() {
        return flowerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        clearError(FIELD_QUANTITY);

        if (quantity <= 0) {
            addError(FIELD_QUANTITY,
                  ValidationError.BOUQUET_FLOWER_QUANTITY_INVALID.message());
        }

        this.quantity = quantity;
    }

    public double getPriceAtCreation() {
        return priceAtCreation;
    }

    public void setPriceAtCreation(double priceAtCreation) {
        clearError(FIELD_PRICE);

        if (priceAtCreation <= 0) {
            addError(FIELD_PRICE,
                  ValidationError.BOUQUET_FLOWER_PRICE_INVALID.message());
        }

        this.priceAtCreation = priceAtCreation;
    }

    @Override
    public String toString() {
        return String.format("%s x%d %.2f",
              flowerId, quantity, priceAtCreation);
    }
}
