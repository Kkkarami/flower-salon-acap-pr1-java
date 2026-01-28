package fedelesh.flowersalon.presentation.form;

import fedelesh.flowersalon.domain.contract.SupplierService;
import fedelesh.flowersalon.domain.contract.SupplyService;
import fedelesh.flowersalon.domain.impl.Supplier;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class SupplyForm {

    private final Scanner scanner = new Scanner(System.in);
    private final SupplyService supplyService;
    private final SupplierService supplierService;

    public SupplyForm(SupplyService supplyService,
          SupplierService supplierService) {
        this.supplyService = supplyService;
        this.supplierService = supplierService;
    }

    public void fill() {
        System.out.println("\n--- ОФОРМЛЕННЯ ПОСТАВКИ ---");

        UUID selectedSupplierId = chooseSupplier();
        if (selectedSupplierId == null) {
            return;
        }

        System.out.print("Назва квітки: ");
        String name = scanner.nextLine();
        System.out.print("Опис: ");
        String desc = scanner.nextLine();
        System.out.print("Ціна: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Кількість: ");
        int qty = Integer.parseInt(scanner.nextLine());

        try {
            supplyService.processSupply(name, desc, price, qty, selectedSupplierId);
            System.out.println("Поставка успішно оброблена.");
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private UUID chooseSupplier() {
        List<Supplier> suppliers = new ArrayList<>(supplierService.getAll());
        if (suppliers.isEmpty()) {
            System.out.println(
                  "У системі немає жодного постачальника! Спочатку додайте постачальника.");
            return null;
        }

        System.out.println("Оберіть постачальника:");
        for (int i = 0; i < suppliers.size(); i++) {
            System.out.println((i + 1) + ". " + suppliers.get(i).getCompanyName());
        }

        try {
            System.out.print("Ваш вибір: ");
            int input = Integer.parseInt(scanner.nextLine());
            int index = input - 1;
            if (index >= 0 && index < suppliers.size()) {
                return suppliers.get(index).getId();
            }
        } catch (Exception e) {
            System.out.println("Некоректний вибір.");
        }
        return null;
    }
}