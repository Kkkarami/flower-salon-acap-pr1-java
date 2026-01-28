package fedelesh.flowersalon.presentation.pages;

import fedelesh.flowersalon.domain.contract.FloristService;
import fedelesh.flowersalon.domain.impl.Florist;
import java.util.Collection;

public class FloristsView {

    private final FloristService floristService;

    public FloristsView(FloristService floristService) {
        this.floristService = floristService;
    }

    public void render() {
        Collection<Florist> florists = floristService.getAll();

        System.out.println("\n=== СПИСОК ПЕРСОНАЛУ САЛОНУ ===");
        System.out.printf("%-15s | %-15s | %-35s | %-10s%n", "Ім'я", "Прізвище", "Email", "Роль");
        System.out.println(
              "-----------------------------------------------------------------------------------------");

        if (florists.isEmpty()) {
            System.out.println("Список порожній.");
        } else {
            for (Florist f : florists) {
                System.out.printf("%-15s | %-15s | %-35s | %-10s%n",
                      f.getFirstName(), f.getLastName(), f.getEmail(), f.getRole());
            }
        }
    }
}
