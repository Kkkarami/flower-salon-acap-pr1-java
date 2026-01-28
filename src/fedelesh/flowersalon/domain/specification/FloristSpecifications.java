package fedelesh.flowersalon.domain.specification;

import fedelesh.flowersalon.domain.impl.Florist;

public final class FloristSpecifications {

    private FloristSpecifications() {
    }

    /**
     * Прізвище містить текст (без урахування регістру).
     */
    public static Specification<Florist> lastNameContains(String text) {
        return florist -> florist.getLastName() != null
              && florist.getLastName().toLowerCase()
              .contains(text.toLowerCase());
    }
}
