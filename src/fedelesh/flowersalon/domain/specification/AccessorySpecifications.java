package fedelesh.flowersalon.domain.specification;

import fedelesh.flowersalon.domain.impl.Accessory;

public final class AccessorySpecifications {

    private AccessorySpecifications() {
    }

    public static Specification<Accessory> byName(String name) {
        return a -> a.getName() != null
              && a.getName().equalsIgnoreCase(name);
    }

    public static Specification<Accessory> nameContains(String text) {
        return a -> a.getName() != null
              && a.getName().toLowerCase().contains(text.toLowerCase());
    }

    public static Specification<Accessory> available() {
        return Accessory::isAvailable;
    }

    public static Specification<Accessory> priceGreaterThan(double price) {
        return a -> a.getPrice() > price;
    }

    public static Specification<Accessory> priceLessThan(double price) {
        return a -> a.getPrice() < price;
    }

    public static Specification<Accessory> all() {
        return a -> true;
    }

    public static Specification<Accessory> none() {
        return a -> false;
    }
}
