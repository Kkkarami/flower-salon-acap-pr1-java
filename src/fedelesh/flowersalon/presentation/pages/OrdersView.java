package fedelesh.flowersalon.presentation.pages;

import fedelesh.flowersalon.domain.contract.OrderService;
import fedelesh.flowersalon.domain.enums.OrderStatus;
import fedelesh.flowersalon.domain.impl.Order;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class OrdersView {

    private final OrderService orderService;
    private final Scanner scanner = new Scanner(System.in);

    public OrdersView(OrderService orderService) {
        this.orderService = orderService;
    }

    public void render() {
        List<Order> orders = orderService.getAll().stream().toList();
        if (orders.isEmpty()) {
            System.out.println("Замовлень поки немає.");
            return;
        }

        System.out.println("\n--- СПИСОК ЗАМОВЛЕНЬ ---");
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            System.out.printf("%d. ID: %s | Сума: %.2f | Статус: %s%n",
                  (i + 1), o.getId().toString().substring(0, 8), o.getTotalPrice(), o.getStatus());
        }

        System.out.print("\nОберіть номер замовлення для зміни статусу (або 'n' для виходу): ");
        String input = scanner.nextLine();
        if (!input.equalsIgnoreCase("n")) {
            try {
                int idx = Integer.parseInt(input) - 1;
                processStatusChange(orders.get(idx).getId());
            } catch (Exception e) {
                System.out.println("Помилка вибору.");
            }
        }
    }

    private void processStatusChange(UUID orderId) {
        System.out.println("Оберіть новий статус:");
        System.out.println("1. Нове (NEW)");
        System.out.println("2. Виконано (COMPLETED)");
        System.out.println("3. В процесі (IN_PROGRESS)");
        System.out.println("4. Відмінено (CANCELLED)");

        String choice = scanner.nextLine();
        OrderStatus newStatus = switch (choice) {
            case "1" -> OrderStatus.NEW;
            case "2" -> OrderStatus.COMPLETED;
            case "3" -> OrderStatus.IN_PROGRESS;
            case "4" -> OrderStatus.CANCELLED;
            default -> null;
        };

        if (newStatus == null) {
            System.out.println("Невірний вибір.");
            return;
        }

        try {
            // Делегуємо логіку збереження сервісу
            orderService.updateStatus(orderId, newStatus);
            System.out.println("Статус оновлено!");
        } catch (Exception e) {
            System.out.println("Помилка при оновленні: " + e.getMessage());
        }
    }
}
