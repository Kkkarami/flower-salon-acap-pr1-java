package fedelesh.flowersalon.presentation.pages;

import fedelesh.flowersalon.domain.contract.AccessoryService;
import fedelesh.flowersalon.domain.contract.AuthService;
import fedelesh.flowersalon.domain.contract.BouquetService;
import fedelesh.flowersalon.domain.contract.FloristService;
import fedelesh.flowersalon.domain.contract.FlowerService;
import fedelesh.flowersalon.domain.contract.OrderService;
import fedelesh.flowersalon.domain.contract.SignUpService;
import fedelesh.flowersalon.domain.contract.SupplierService;
import fedelesh.flowersalon.domain.contract.SupplyService;
import fedelesh.flowersalon.domain.enums.WorkerRole;
import fedelesh.flowersalon.presentation.form.AccessoryForm;
import fedelesh.flowersalon.presentation.form.CreateBouquetForm;
import fedelesh.flowersalon.presentation.form.OrderCreateForm;
import fedelesh.flowersalon.presentation.form.SupplierForm;
import fedelesh.flowersalon.presentation.form.SupplyForm;
import java.util.Scanner;

public class MainMenuView {

    private final AuthService authService;
    private final SignUpService signUpService;
    private final OrderService orderService;
    private final AccessoryService accessoryService;
    private final BouquetService bouquetService;
    private final SupplyService supplyService;
    private final SupplierService supplierService;
    private final FloristService floristService;
    private final FlowerService flowerService;
    private final Scanner scanner = new Scanner(System.in);

    public MainMenuView(AuthService auth, SignUpService sign, OrderService orderServ,
          AccessoryService accServ, BouquetService bouquetServ, SupplyService supplyServ,
          SupplierService supplierServ, FloristService floristServ, FlowerService flowerServ) {
        this.authService = auth;
        this.signUpService = sign;
        this.orderService = orderServ;
        this.accessoryService = accServ;
        this.bouquetService = bouquetServ;
        this.supplyService = supplyServ;
        this.supplierService = supplierServ;
        this.floristService = floristServ;
        this.flowerService = flowerServ;
    }

    public void show() {
        while (true) {
            System.out.println("\n--- СИСТЕМА ОБЛІКУ КВІТКОВОГО САЛОНУ ---");
            System.out.println("1. Центр пошуку (Квіти/Аксесуари/Флористи)");
            System.out.println("--- КВІТИ ТА БУКЕТИ ---");
            System.out.println("2. Каталог (Квіти та Букети)");
            System.out.println("3. Скласти новий букет");
            System.out.println("--- ЗАМОВЛЕННЯ ---");
            System.out.println("4. Створити замовлення (Продаж)");
            System.out.println("5. Переглянути замовлення");
            System.out.println("--- АКСЕСУАРИ ---");
            System.out.println("6. Керування аксесуарами");

            if (isOwner()) {
                System.out.println("--- УПРАВЛІННЯ ПОСТАВКАМИ ---");
                System.out.println("7. Прийняти поставку квітів");
                System.out.println("8. Керування постачальниками");
                System.out.println("--- УПРАВЛІННЯ ПЕРСОНАЛОМ ---");
                System.out.println("9. Меню управління персоналом");
            }

            System.out.println("0. Вихід");
            System.out.print("Дія: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" ->
                      new SearchView(flowerService, accessoryService, floristService).render();
                case "2" -> new FlowersView(flowerService, bouquetService).render();
                case "3" ->
                      new CreateBouquetForm(bouquetService, flowerService, accessoryService).fill();
                case "4" -> new OrderCreateForm(
                      orderService,
                      authService,
                      flowerService,
                      bouquetService,
                      accessoryService
                ).fill();
                case "5" -> new OrdersView(orderService).render();
                case "6" -> new AccessoryForm(accessoryService).renderMenu();
                case "7" -> {
                    if (isOwner()) {
                        new SupplyForm(supplyService, supplierService).fill();
                    } else {
                        System.out.println("Доступ заборонено (тільки для власника).");
                    }
                }
                case "8" -> {
                    if (isOwner()) {
                        new SupplierForm(supplierService).renderMenu();
                    } else {
                        System.out.println("Доступ заборонено (тільки для власника).");
                    }
                }
                case "9" -> {
                    if (isOwner()) {
                        new WorkerManagementView(signUpService, floristService).render();
                    } else {
                        System.out.println("Доступ заборонено (тільки для власника).");
                    }
                }
                case "0" -> {
                    authService.logout();
                    return;
                }
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private boolean isOwner() {
        return authService.getUser() != null && authService.getUser().getRole() == WorkerRole.OWNER;
    }
}
