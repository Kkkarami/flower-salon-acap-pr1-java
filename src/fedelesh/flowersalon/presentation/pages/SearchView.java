package fedelesh.flowersalon.presentation.pages;

import fedelesh.flowersalon.domain.contract.AccessoryService;
import fedelesh.flowersalon.domain.contract.FloristService;
import fedelesh.flowersalon.domain.contract.FlowerService;
import fedelesh.flowersalon.domain.specification.AccessorySpecifications;
import fedelesh.flowersalon.domain.specification.FloristSpecifications;
import fedelesh.flowersalon.domain.specification.FlowerSpecifications;
import java.util.Scanner;

public class SearchView {

    private final FlowerService flowerService;
    private final AccessoryService accessoryService;
    private final FloristService floristService;
    private final Scanner scanner = new Scanner(System.in);

    public SearchView(FlowerService flowerService,
          AccessoryService accessoryService,
          FloristService floristService) {
        this.flowerService = flowerService;
        this.accessoryService = accessoryService;
        this.floristService = floristService;
    }

    public void render() {
        while (true) {
            System.out.println("\n--- ЦЕНТР ПОШУКУ ТА ФІЛЬТРАЦІЇ ---");
            System.out.println("1. Знайти квіти (за назвою)");
            System.out.println("2. Знайти аксесуари (за назвою)");
            System.out.println("3. Знайти флористів (за прізвищем)");
            System.out.println("0. Повернутися до головного меню");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            if (choice.equals("0")) {
                break;
            }

            System.out.print("Введіть текст для пошуку: ");
            String query = scanner.nextLine();

            switch (choice) {
                case "1" -> searchFlowers(query);
                case "2" -> searchAccessories(query);
                case "3" -> searchFlorists(query);
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private void searchFlowers(String query) {
        var found = flowerService.getAll(FlowerSpecifications.nameContains(query).toPredicate());

        System.out.println("\nРезультати пошуку квітів:");
        if (found.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            found.forEach(f -> System.out.printf("- %s | Ціна: %.2f | Кількість: %d%n",
                  f.getName(), f.getPrice(), f.getQuantity()));
        }
    }

    private void searchAccessories(String query) {
        var found = accessoryService.getAll(
              AccessorySpecifications.nameContains(query).toPredicate());

        System.out.println("\nРезультати пошуку аксесуарів:");
        if (found.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            found.forEach(a -> System.out.printf("- %s (%s) | %.2f грн | Доступність: %s%n",
                  a.getName(), a.getDescription(), a.getPrice(), a.isAvailable() ? "Є" : "Немає"));
        }
    }

    private void searchFlorists(String query) {
        var found = floristService.getAll(
              FloristSpecifications.lastNameContains(query).toPredicate());

        System.out.println("\nРезультати пошуку флористів:");
        if (found.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            found.forEach(f -> System.out.printf("- %s %s [%s]%n",
                  f.getFirstName(), f.getLastName(), f.getRole()));
        }
    }
}
