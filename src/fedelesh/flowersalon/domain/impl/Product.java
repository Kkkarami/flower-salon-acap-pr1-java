package fedelesh.flowersalon.domain.impl;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.exception.EntityValidationException;
import fedelesh.flowersalon.domain.util.ValidationError;
import java.util.UUID;

public class Product extends BaseEntity {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_PRICE = "price";

    private String name;
    private String description;
    private double price;
    private boolean available;

    private Product() {
        super();
    }

    public Product(UUID id, String name, String description, double price, boolean available) {
        this();
        setName(name);
        setDescription(description);
        setPrice(price);
        setAvailable(available);

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f %s", name, price, available ? "доступний" : "не доступний");
    }
}
