package fedelesh.flowersalon.domain.impl;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.exception.EntityValidationException;
import fedelesh.flowersalon.domain.util.ValidationError;
import java.util.UUID;

public class Flower extends BaseEntity {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_QUANTITY = "quantity";

    private String name;
    private String description;
    private double price;
    private int quantity;
    private UUID supplierId;

    private Flower() {
        super();
    }

    public Flower(String name, String description, double price, int quantity,
          UUID supplierId) {
        this();
        setName(name);
        setDescription(description);
        setPrice(price);
        this.supplierId = supplierId;

        if (!isValid()) {
            throw new EntityValidationException(getErrors());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        clearError(FIELD_NAME);
        if (name == null || name.trim().isEmpty()) {
            addError(FIELD_NAME, ValidationError.PRODUCT_NAME_EMPTY.message());
        }
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        clearError(FIELD_PRICE);
        if (price <= 0) {
            addError(FIELD_PRICE, ValidationError.PRODUCT_PRICE_INVALID.message());
        }
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        clearError(FIELD_QUANTITY);

        if (quantity < 0) {
            addError(FIELD_QUANTITY, ValidationError.PRODUCT_QUANTITY_MINUS.message());
        }

        this.quantity = quantity;
    }

    public UUID getSupplierId() {
        return supplierId;
    }
}
