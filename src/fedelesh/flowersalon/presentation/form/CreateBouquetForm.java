package fedelesh.flowersalon.presentation.form;

import fedelesh.flowersalon.domain.contract.AccessoryService;
import fedelesh.flowersalon.domain.contract.BouquetService;
import fedelesh.flowersalon.domain.contract.FlowerService;
import fedelesh.flowersalon.domain.dto.BouquetCreateDto;
import fedelesh.flowersalon.domain.impl.Accessory;
import fedelesh.flowersalon.domain.impl.Flower;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class CreateBouquetForm {

    private final Scanner scanner = new Scanner(System.in);
    private final BouquetService bouquetService;
    private final FlowerService flowerService;
    private final AccessoryService accessoryService; // Додано сервіс аксесуарів

    public CreateBouquetForm(BouquetService bouquetService,
          FlowerService flowerService,
          AccessoryService accessoryService) {
        this.bouquetService = bouquetService;
        this.flowerService = flowerService;
        this.accessoryService = accessoryService;
    }

    public void fill() {
        System.out.println("\n--- СТВОРЕННЯ НОВОГО БУКЕТА ---");
        System.out.print("Введіть назву для букета: ");
        String bouquetName = scanner.nextLine();

        Map<UUID, Integer> flowerIdsWithQuantities = new HashMap<>();

        while (true) {
            List<Flower> availableFlowers = flowerService.getAll().stream().toList();

            System.out.println("\nОберіть квіти для складу букета:");
            for (int i = 0; i < availableFlowers.size(); i++) {
                Flower f = availableFlowers.get(i);
                int currentInCart = flowerIdsWithQuantities.getOrDefault(f.getId(), 0);
                int realStock = f.getQuantity() - currentInCart;
                System.out.println((i + 1) + ". " + f.getName() + " (залишок: " + realStock + ")");
            }
            System.out.println("n. Перейти до вибору упаковки");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("n")) {
                break;
            }

            try {
                int index = Integer.parseInt(choice) - 1;
                Flower selected = availableFlowers.get(index);

                System.out.print("Кількість " + selected.getName() + ": ");
                int qty = Integer.parseInt(scanner.nextLine());

                int currentInCart = flowerIdsWithQuantities.getOrDefault(selected.getId(), 0);
                if (qty > 0 && (currentInCart + qty) <= selected.getQuantity()) {
                    flowerIdsWithQuantities.put(selected.getId(), currentInCart + qty);
                    System.out.println("Додано!");
                } else {
                    System.out.println("Помилка: Недостатньо квітів.");
                }
            } catch (Exception e) {
                System.out.println("Невірний вибір.");
            }
        }

        UUID wrapperId = selectAccessory();

        try {
            if (flowerIdsWithQuantities.isEmpty()) {
                System.out.println("Букет не може бути порожнім!");
                return;
            }
            BouquetCreateDto dto = new BouquetCreateDto(bouquetName, flowerIdsWithQuantities,
                  wrapperId);
            bouquetService.create(dto);
            System.out.println("Букет '" + bouquetName + "' успішно створено!");
        } catch (Exception e) {
            System.out.println("Помилка створення: " + e.getMessage());
        }
    }

    private UUID selectAccessory() {
        // Тепер використовуємо поле класу accessoryService
        List<Accessory> accessories = accessoryService.getAvailable();
        if (accessories.isEmpty()) {
            System.out.println("\nДоступних упаковок немає.");
            return null;
        }

        System.out.println("\nОберіть упаковку (аксесуар):");
        for (int i = 0; i < accessories.size(); i++) {
            Accessory a = accessories.get(i);
            System.out.println((i + 1) + ". " + a.getName() + " (Ціна: " + a.getPrice() + ")");
        }
        System.out.println("n. Без аксесуара");
        System.out.print("Ваш вибір: ");

        String accChoice = scanner.nextLine();
        if (accChoice.equalsIgnoreCase("n")) {
            return null;
        }

        try {
            int index = Integer.parseInt(accChoice) - 1;
            return accessories.get(index).getId();
        } catch (Exception e) {
            System.out.println("Упаковку не обрано.");
            return null;
        }
    }
}
