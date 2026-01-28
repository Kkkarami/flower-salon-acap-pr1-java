package fedelesh.flowersalon.presentation.form;

import fedelesh.flowersalon.domain.contract.AccessoryService;
import fedelesh.flowersalon.domain.contract.AuthService;
import fedelesh.flowersalon.domain.contract.BouquetService;
import fedelesh.flowersalon.domain.contract.FlowerService;
import fedelesh.flowersalon.domain.contract.OrderService;
import fedelesh.flowersalon.domain.dto.OrderCreateDto;
import fedelesh.flowersalon.domain.impl.Flower;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class OrderCreateForm {

    private final Scanner scanner = new Scanner(System.in);
    private final OrderService orderService;
    private final AuthService authService;
    private final FlowerService flowerService;
    private final AccessoryService accessoryService;
    private final BouquetService bouquetService;

    public OrderCreateForm(OrderService orderService,
          AuthService authService,
          FlowerService flowerService,
          BouquetService bouquetService,
          AccessoryService accessoryService) {
        this.orderService = orderService;
        this.authService = authService;
        this.flowerService = flowerService;
        this.bouquetService = bouquetService;
        this.accessoryService = accessoryService;
    }

    public void fill() {
        Map<UUID, Integer> basket = new HashMap<>();

        while (true) {
            renderMenu();
            String choice = scanner.nextLine();
            if (choice.equals("0")) {
                return;
            }
            if (choice.equalsIgnoreCase("s")) {
                break;
            }

            switch (choice) {
                case "1" -> addFlowers(basket);
                case "2" -> addBouquets(basket);
                case "3" -> addAccessories(basket);
                default -> System.out.println("Невірний вибір.");
            }
        }
        processConfirmation(basket);
    }

    private void renderMenu() {
        System.out.println("\n--- ФОРМУВАННЯ ЗАМОВЛЕННЯ ---");
        System.out.println("1. Додати Квіти");
        System.out.println("2. Додати Букети");
        System.out.println("3. Додати Аксесуари");
        System.out.println("s. ПІДТВЕРДИТИ");
        System.out.println("0. Назад");
        System.out.print("Вибір: ");
    }

    private void addFlowers(Map<UUID, Integer> basket) {
        var flowers = flowerService.getAll().stream().toList();
        if (flowers.isEmpty()) {
            System.out.println("Квітів немає в наявності.");
            return;
        }

        for (int i = 0; i < flowers.size(); i++) {
            Flower f = flowers.get(i);
            int inBasket = basket.getOrDefault(f.getId(), 0);
            System.out.println(
                  (i + 1) + ". " + f.getName() + " (доступно: " + (f.getQuantity() - inBasket)
                        + ")");
        }

        try {
            System.out.print("Оберіть номер: ");
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            Flower f = flowers.get(idx);

            System.out.print("Кількість: ");
            int qty = Integer.parseInt(scanner.nextLine());

            int currentInBasket = basket.getOrDefault(f.getId(), 0);
            if (qty > 0 && (currentInBasket + qty) <= f.getQuantity()) {
                basket.put(f.getId(), currentInBasket + qty);
                System.out.println("Додано!");
            } else {
                System.out.println("Помилка: Недостатньо товару.");
            }
        } catch (Exception e) {
            System.out.println("Помилка вибору.");
        }
    }

    private void addBouquets(Map<UUID, Integer> basket) {
        var bouquets = bouquetService.getAll().stream().toList();
        if (bouquets.isEmpty()) {
            System.out.println("Букетів не знайдено.");
            return;
        }

        for (int i = 0; i < bouquets.size(); i++) {
            System.out.println((i + 1) + ". " + bouquets.get(i).getName());
        }

        try {
            System.out.print("Оберіть номер: ");
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            UUID id = bouquets.get(idx).getId();
            basket.put(id, basket.getOrDefault(id, 0) + 1);
            System.out.println("Букет додано!");
        } catch (Exception e) {
            System.out.println("Помилка вибору.");
        }
    }

    private void addAccessories(Map<UUID, Integer> basket) {
        var availableAccs = accessoryService.getAvailable();
        if (availableAccs.isEmpty()) {
            System.out.println("Доступних аксесуарів немає.");
            return;
        }

        for (int i = 0; i < availableAccs.size(); i++) {
            System.out.println((i + 1) + ". " + availableAccs.get(i).getName());
        }

        try {
            System.out.print("Оберіть номер: ");
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            UUID id = availableAccs.get(idx).getId();
            basket.put(id, basket.getOrDefault(id, 0) + 1);
            System.out.println("Аксесуар додано!");
        } catch (Exception e) {
            System.out.println("Помилка вибору.");
        }
    }

    private void processConfirmation(Map<UUID, Integer> basket) {
        if (basket.isEmpty()) {
            System.out.println("Кошик порожній.");
            return;
        }
        try {
            UUID workerId = authService.getUser().getId();
            orderService.create(new OrderCreateDto(workerId, basket));
            System.out.println("Замовлення успішно створено.");
        } catch (Exception e) {
            System.out.println("Помилка сервісу: " + e.getMessage());
        }
    }
}
