package fedelesh.flowersalon.domain.specification;

import fedelesh.flowersalon.domain.impl.Flower;
import java.util.UUID;

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

    public static Specification<Flower> available() {
        return Flower::isAvailable;
    }

    public static Specification<Flower> bySupplier(UUID supplierId) {
        return f -> f.getSupplierId() != null
              && f.getSupplierId().equals(supplierId);
    }

    public static Specification<Flower> priceGreaterThan(double price) {
        return f -> f.getPrice() > price;
    }

    public static Specification<Flower> all() {
        return f -> true;
    }

    public static Specification<Flower> none() {
        return f -> false;
    }
}
