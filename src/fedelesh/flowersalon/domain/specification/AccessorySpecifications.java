package fedelesh.flowersalon.domain.specification;

import fedelesh.flowersalon.domain.impl.Accessory;

public final class AccessorySpecifications {

    private AccessorySpecifications() {
    }

    public static Specification<Accessory> nameContains(String text) {
        return a -> a.getName() != null
              && a.getName().toLowerCase().contains(text.toLowerCase());
    }
}
