package fedelesh.flowersalon.domain.specification;

import fedelesh.flowersalon.domain.impl.Flower;

public final class FlowerSpecifications {

    private FlowerSpecifications() {
    }

    public static Specification<Flower> byName(String name) {
        return f -> f.getName() != null
              && f.getName().equalsIgnoreCase(name);
    }

    public static Specification<Flower> nameContains(String text) {
        return f -> f.getName() != null
              && f.getName().toLowerCase().contains(text.toLowerCase());
    }
}
