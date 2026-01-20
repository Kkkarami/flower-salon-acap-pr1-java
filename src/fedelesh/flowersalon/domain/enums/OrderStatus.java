package fedelesh.flowersalon.domain.enums;

public enum OrderStatus {
    NEW("Новий"), IN_PROGRESS("В процесі"), COMPLETED("Виконаний"), CANCELLED("Скасований");

    private final Object name;

    OrderStatus(String name) {
        this.name = name;
    }

    public Object getName() {
        return name;
    }
}