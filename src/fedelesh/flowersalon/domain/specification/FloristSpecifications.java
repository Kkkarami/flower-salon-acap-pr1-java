package fedelesh.flowersalon.domain.specification;

import fedelesh.flowersalon.domain.enums.WorkerRole;
import fedelesh.flowersalon.domain.impl.Florist;

public final class FloristSpecifications {

    private FloristSpecifications() {
        // Утилітний клас
    }

    /**
     * Ім'я містить текст (без урахування регістру).
     */
    public static Specification<Florist> firstNameContains(String text) {
        return florist -> florist.getFirstName() != null
              && florist.getFirstName().toLowerCase()
              .contains(text.toLowerCase());
    }

    /**
     * Прізвище містить текст (без урахування регістру).
     */
    public static Specification<Florist> lastNameContains(String text) {
        return florist -> florist.getLastName() != null
              && florist.getLastName().toLowerCase()
              .contains(text.toLowerCase());
    }

    /**
     * Повне ім'я містить текст (без урахування регістру).
     */
    public static Specification<Florist> fullNameContains(String text) {
        return florist -> {
            if (florist.getFirstName() == null || florist.getLastName() == null) {
                return false;
            }
            String fullName = florist.getFirstName() + " " + florist.getLastName();
            return fullName.toLowerCase().contains(text.toLowerCase());
        };
    }

    /**
     * За номером телефону.
     */
    public static Specification<Florist> byEmail(String email) {
        return florist -> florist.getEmail() != null
              && florist.getEmail().equals(email);
    }

    /**
     * За роллю.
     */
    public static Specification<Florist> byRole(WorkerRole role) {
        return florist -> florist.getRole() == role;
    }

    /**
     * Є власником
     */
    public static Specification<Florist> isOwner() {
        return florist -> florist.getRole() == WorkerRole.OWNER;
    }

    /**
     * Є звичайним флористом
     */
    public static Specification<Florist> isWorker() {
        return florist -> florist.getRole() == WorkerRole.WORKER;
    }

    /**
     * Всі флористи
     */
    public static Specification<Florist> all() {
        return florist -> true;
    }

    /**
     * Жодного флориста
     */
    public static Specification<Florist> none() {
        return florist -> false;
    }
}
