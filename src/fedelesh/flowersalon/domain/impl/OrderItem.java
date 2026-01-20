package fedelesh.flowersalon.domain.impl;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.exception.EntityValidationException;
import fedelesh.flowersalon.domain.util.ValidationError;
import java.util.UUID;

public class OrderItem extends BaseEntity {

    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_PRICE = "priceAtOrderTime";

    private final UUID orderId;
    private final UUID productId;
    private int quantity;
    private double priceAtOrderTime;

    public OrderItem(UUID orderId, UUID productId,
          int quantity, double priceAtOrderTime) {

        setQuantity(quantity);
        setPriceAtOrderTime(priceAtOrderTime);

        this.orderId = orderId;
        this.productId = productId;

        if (!isValid()) {
            throw new EntityValidationException(getErrors());
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        clearError(FIELD_QUANTITY);
        if (quantity <= 0) {
            addError(FIELD_QUANTITY, ValidationError.QUANTITY_INVALID.message());
        }
        this.quantity = quantity;
    }

    public double getPriceAtOrderTime() {
        return priceAtOrderTime;
    }

    public void setPriceAtOrderTime(double price) {
        clearError(FIELD_PRICE);
        if (price <= 0) {
            addError(FIELD_PRICE, ValidationError.PRICE_AT_ORDER_INVALID.message());
        }
        this.priceAtOrderTime = price;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d %.2f", orderId, productId, quantity, priceAtOrderTime);
    }
}
