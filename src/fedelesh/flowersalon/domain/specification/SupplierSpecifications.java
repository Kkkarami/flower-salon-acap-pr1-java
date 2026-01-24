package fedelesh.flowersalon.domain.specification;

import fedelesh.flowersalon.domain.impl.Supplier;

public final class SupplierSpecifications {

    private SupplierSpecifications() {
        // Утилітний клас
    }

    /**
     * Точний пошук за назвою компанії.
     */
    public static Specification<Supplier> byCompanyName(String name) {
        return supplier -> supplier.getCompanyName() != null
              && supplier.getCompanyName().equalsIgnoreCase(name);
    }

    /**
     * Назва компанії містить текст (без урахування регістру).
     */
    public static Specification<Supplier> companyNameContains(String text) {
        return supplier -> supplier.getCompanyName() != null
              && supplier.getCompanyName().toLowerCase()
              .contains(text.toLowerCase());
    }

    /**
     * Контактна особа містить текст (без урахування регістру).
     */
    public static Specification<Supplier> contactPersonContains(String text) {
        return supplier -> supplier.getContactPerson() != null
              && supplier.getContactPerson().toLowerCase()
              .contains(text.toLowerCase());
    }

    /**
     * Точний пошук за номером телефону.
     */
    public static Specification<Supplier> byPhoneNumber(String phoneNumber) {
        return supplier -> supplier.getPhoneNumber() != null
              && supplier.getPhoneNumber().equals(phoneNumber);
    }

    /**
     * Має номер телефону.
     */
    public static Specification<Supplier> hasPhoneNumber() {
        return supplier -> supplier.getPhoneNumber() != null
              && !supplier.getPhoneNumber().trim().isEmpty();
    }

    /**
     * Всі постачальники.
     */
    public static Specification<Supplier> all() {
        return supplier -> true;
    }

    /**
     * Жодного постачальника.
     */
    public static Specification<Supplier> none() {
        return supplier -> false;
    }
}
