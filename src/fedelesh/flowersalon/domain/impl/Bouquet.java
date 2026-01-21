package fedelesh.flowersalon.domain.impl;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.exception.EntityValidationException;
import fedelesh.flowersalon.domain.util.ValidationError;

public class Bouquet extends BaseEntity {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_DESCRIPTION = "description";

    private String name;
    private double price;
    private String description;

    private Bouquet() {
        super();
    }

    public Bouquet(String name, double price, String description) {
        this();
        setName(name);
        setPrice(price);
        setDescription(description);

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
            addError(FIELD_NAME, ValidationError.BOUQUET_NAME_EMPTY.message());
        } else if (name.length() < 5) {
            addError(FIELD_NAME, ValidationError.BOUQUET_NAME_TOO_SHORT.message());
        }

        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        clearError(FIELD_PRICE);

        if (price <= 0) {
            addError(FIELD_PRICE, ValidationError.BOUQUET_PRICE_INVALID.message());
        }

        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        clearError(FIELD_DESCRIPTION);

        if (description == null || description.trim().isEmpty()) {
            addError(FIELD_DESCRIPTION, ValidationError.BOUQUET_DESCRIPTION_EMPTY.message());
        }

        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f %s", name, price, description);
    }
}
