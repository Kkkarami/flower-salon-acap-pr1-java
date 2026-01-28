package fedelesh.flowersalon.presentation.form;

import fedelesh.flowersalon.domain.contract.SupplierService;
import fedelesh.flowersalon.domain.dto.SupplierCreateDto;
import java.util.Scanner;

public class SupplierForm {

    private final Scanner scanner = new Scanner(System.in);
    private final SupplierService supplierService;

    public SupplierForm(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public void renderMenu() {
        while (true) {
            System.out.println("\n--- КЕРУВАННЯ ПОСТАЧАЛЬНИКАМИ ---");
            System.out.println("1. Список постачальників");
            System.out.println("2. Додати нового постачальника");
            System.out.println("0. Назад");
            System.out.print("Вибір: ");
            String choice = scanner.nextLine();

            if (choice.equals("0")) {
                return;
            }

            switch (choice) {
                case "1" -> showSuppliers();
                case "2" -> addSupplier();
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private void showSuppliers() {
        var suppliers = supplierService.getAll();
        if (suppliers.isEmpty()) {
            System.out.println("Список порожній.");
            return;
        }
        System.out.println("\nДіючі постачальники:");
        suppliers.forEach(System.out::println);
    }

    private void addSupplier() {
        try {
            System.out.print("Назва компанії: ");
            String name = scanner.nextLine();
            System.out.print("Контактна особа: ");
            String person = scanner.nextLine();
            System.out.print("Номер телефону: ");
            String phone = scanner.nextLine();

            SupplierCreateDto dto = new SupplierCreateDto(name, person, phone);
            supplierService.add(dto);
            System.out.println("Постачальника успішно додано.");
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}
