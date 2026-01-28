package fedelesh.flowersalon.presentation.form;

import fedelesh.flowersalon.domain.contract.AccessoryService;
import fedelesh.flowersalon.domain.dto.AccessoryAddDto;
import fedelesh.flowersalon.domain.impl.Accessory;
import java.util.List;
import java.util.Scanner;

public class AccessoryForm {

    private final Scanner scanner = new Scanner(System.in);
    private final AccessoryService accessoryService;

    public AccessoryForm(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    public void renderMenu() {
        while (true) {
            System.out.println("\n--- КЕРУВАННЯ АКСЕСУАРАМИ ---");
            System.out.println("1. Переглянути наявні");
            System.out.println("2. Додати новий аксесуар");
            System.out.println("3. Змінити доступність");
            System.out.println("0. Назад");
            System.out.print("Вибір: ");
            String choice = scanner.nextLine();
            if (choice.equals("0")) {
                break;
            }

            switch (choice) {
                case "1" -> showAccessories();
                case "2" -> addAccessory();
                case "3" -> toggleAvailability();
            }
        }
    }

    private void showAccessories() {
        var list = accessoryService.getAll();
        System.out.printf("%-20s | %-10s | %-15s | %-10s%n", "Назва", "Ціна", "Статус", "Опис");
        list.forEach(a -> System.out.printf("%-20s | %-10.2f | %-15s | %-10s%n",
              a.getName(),
              a.getPrice(),
              a.isAvailable() ? "Доступно" : "Недоступно",
              a.getDescription()));
    }

    private void addAccessory() {
        try {
            System.out.print("Назва нового аксесуара: ");
            String name = scanner.nextLine();
            System.out.print("Опис: ");
            String desc = scanner.nextLine();
            System.out.print("Ціна: ");
            double price = Double.parseDouble(scanner.nextLine());

            AccessoryAddDto dto = new AccessoryAddDto(name, price, desc, true);
            accessoryService.add(dto);

            System.out.println("Аксесуар успішно додано.");
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void toggleAvailability() {
        List<Accessory> accessories = accessoryService.getAll().stream().toList();
        if (accessories.isEmpty()) {
            System.out.println("Список аксесуарів порожній.");
            return;
        }

        System.out.println("\nОберіть аксесуар для зміни статусу:");
        for (int i = 0; i < accessories.size(); i++) {
            Accessory a = accessories.get(i);
            String status = a.isAvailable() ? "[Доступно]" : "[Недоступно]";
            System.out.printf("%d. %s %s%n", (i + 1), a.getName(), status);
        }

        System.out.print("Введіть номер: ");
        try {
            int input = Integer.parseInt(scanner.nextLine());
            int index = input - 1;

            if (index >= 0 && index < accessories.size()) {
                Accessory selected = accessories.get(index);

                accessoryService.toggleAvailability(selected.getId());

                Accessory updated = accessoryService.get(selected.getId());
                String newStatusLabel = updated.isAvailable() ? "ДОСТУПНИЙ" : "НЕДОСТУПНИЙ";
                System.out.println(
                      "Статус оновлено. Тепер " + updated.getName() + " " + newStatusLabel);
            } else {
                System.out.println("Невірний номер у списку.");
            }
        } catch (Exception e) {
            System.out.println("Помилка введення. Очікується число.");
        }
    }
}
