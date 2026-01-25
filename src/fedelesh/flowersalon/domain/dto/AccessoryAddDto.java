package fedelesh.flowersalon.domain.dto;

import fedelesh.flowersalon.domain.BaseEntity;

public final class AccessoryAddDto extends BaseEntity {

    private final String name;
    private final double price;
    private final String description;
    private final boolean available;

    public AccessoryAddDto(String name, double price, String description, boolean available) {
        super();
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;

        if (name == null || name.isBlank()) {
            addError("name", "Назва обов'язкова");
        }
        if (price <= 0) {
            addError("price", "Ціна повинна бути більшою за 0");
        }
        if (description == null || description.isBlank()) {
            addError("description", "Опис обов'язковий");
        }

        if (!isValid()) {
            throw new IllegalArgumentException("Помилка валідації DTO аксесуара");
        }
    }

    public String name() {
        return name;
    }

    public double price() {
        return price;
    }

    public String description() {
        return description;
    }

    public boolean available() {
        return available;
    }
}
