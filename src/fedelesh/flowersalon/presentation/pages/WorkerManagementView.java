package fedelesh.flowersalon.presentation.pages;

import fedelesh.flowersalon.domain.contract.FloristService;
import fedelesh.flowersalon.domain.contract.SignUpService;
import fedelesh.flowersalon.domain.enums.WorkerRole;
import fedelesh.flowersalon.domain.impl.Florist;
import fedelesh.flowersalon.presentation.form.AddWorkerForm;
import java.util.List;
import java.util.Scanner;

public class WorkerManagementView {

    private final Scanner scanner = new Scanner(System.in);
    private final SignUpService signUpService;
    private final FloristService floristService;

    public WorkerManagementView(SignUpService signUpService, FloristService floristService) {
        this.signUpService = signUpService;
        this.floristService = floristService;
    }

    public void render() {
        while (true) {
            System.out.println("\n--- УПРАВЛІННЯ ПЕРСОНАЛОМ ---");
            System.out.println("1. Список усіх працівників");
            System.out.println("2. Зареєструвати нового флориста");
            System.out.println("3. Видалити працівника");
            System.out.println("0. Назад");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            if (choice.equals("0")) {
                break;
            }

            switch (choice) {
                case "1" -> showAllWorkers();
                case "2" -> new AddWorkerForm(signUpService).fill();
                case "3" -> deleteWorker();
                default -> System.out.println("Невірний пункт.");
            }
        }
    }

    private void showAllWorkers() {
        var workers = floristService.getAll();
        System.out.printf("%-20s | %-15s | %-10s%n", "Email", "Ім'я", "Роль");
        workers.forEach(w -> System.out.printf("%-20s | %-15s | %-10s%n",
              w.getEmail(), w.getFirstName(), w.getRole()));
    }

    public void deleteWorker() {
        List<Florist> workers = floristService.getAll().stream().toList();
        System.out.println("\n--- ВИДАЛЕННЯ ПРАЦІВНИКА ---");
        for (int i = 0; i < workers.size(); i++) {
            var w = workers.get(i);
            System.out.printf("%d. %s %s (%s)%n", (i + 1), w.getFirstName(), w.getLastName(),
                  w.getRole());
        }
        System.out.print("Введіть номер для видалення (або '00' для скасування): ");
        try {
            String input = scanner.nextLine();
            if (input.equals("00")) {
                return;
            }
            int idx = Integer.parseInt(input) - 1;
            var toDelete = workers.get(idx);

            if (toDelete.getRole() == WorkerRole.OWNER) {
                System.out.println("Помилка: Неможливо видалити власника!");
                return;
            }

            floristService.remove(toDelete);
            System.out.println("Працівника видалено.");
        } catch (Exception e) {
            System.out.println("Помилка введення.");
        }
    }
}
