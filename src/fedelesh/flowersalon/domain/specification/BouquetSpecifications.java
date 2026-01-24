package fedelesh.flowersalon.domain.specification;

import fedelesh.flowersalon.domain.impl.Bouquet;

public final class BouquetSpecifications {

    private BouquetSpecifications() {
    }

    public static Specification<Bouquet> byName(String name) {
        return b -> b.getName() != null
              && b.getName().equalsIgnoreCase(name);
    }

    public static Specification<Bouquet> nameContains(String text) {
        return b -> b.getName() != null
              && b.getName().toLowerCase().contains(text.toLowerCase());
    }

    public static Specification<Bouquet> priceGreaterThan(double price) {
        return b -> b.getPrice() > price;
    }

    public static Specification<Bouquet> priceLessThan(double price) {
        return b -> b.getPrice() < price;
    }

    public static Specification<Bouquet> all() {
        return b -> true;
    }

    public static Specification<Bouquet> none() {
        return b -> false;
    }
}
