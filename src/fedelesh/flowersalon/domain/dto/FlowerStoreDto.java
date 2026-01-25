package fedelesh.flowersalon.domain.dto;

import fedelesh.flowersalon.domain.BaseEntity;

public final class FlowerStoreDto extends BaseEntity {

    private final String name;
    private final double price;
    private final String description;

    public FlowerStoreDto(String name, double price, String description) {
        super();
        this.name = validatedName(name);
        this.price = validatedPrice(price);
        this.description = description;
        ensureNoErrors();
    }

    private String validatedName(String name) {
        if (name == null || name.isBlank()) {
            addError("name", "Назва квітки обов'язкова");
        }
        return name;
    }

    private double validatedPrice(double price) {
        if (price <= 0) {
            addError("price", "Ціна повинна бути більшою за 0");
        }
        return price;
    }

    private void ensureNoErrors() {
        if (!isValid()) {
            throw new IllegalArgumentException("Помилка даних квітки: " + getErrors());
        }
    }

    public String name() {
        return name;
    }

    public double price() {
        return price;
    }

    public String color() {
        return description;
    }
}
