package fedelesh.flowersalon.domain.enums;

public enum WorkerRole {
    OWNER("Власник"), WORKER("Продавець");

    private final Object name;

    WorkerRole(String name) {
        this.name = name;
    }

    public Object getName() {
        return name;
    }
}
