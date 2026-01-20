package fedelesh.flowersalon.domain.enums;

public enum UserRole {
    CLIENT("Клієнт"), OWNER("Власник"), WORKER("Продавець");

    private final Object name;

    UserRole(String name) {
        this.name = name;
    }

    public Object getName() {
        return name;
    }
}
