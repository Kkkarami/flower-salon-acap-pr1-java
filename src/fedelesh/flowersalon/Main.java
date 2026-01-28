package fedelesh.flowersalon;

import fedelesh.flowersalon.domain.contract.AccessoryService;
import fedelesh.flowersalon.domain.contract.AuthService;
import fedelesh.flowersalon.domain.contract.BouquetService;
import fedelesh.flowersalon.domain.contract.FloristService;
import fedelesh.flowersalon.domain.contract.FlowerService;
import fedelesh.flowersalon.domain.contract.OrderService;
import fedelesh.flowersalon.domain.contract.SignUpService;
import fedelesh.flowersalon.domain.contract.SupplierService;
import fedelesh.flowersalon.domain.contract.SupplyService;
import fedelesh.flowersalon.domain.service.AccessoryServiceImpl;
import fedelesh.flowersalon.domain.service.AuthServiceImpl;
import fedelesh.flowersalon.domain.service.BouquetServiceImpl;
import fedelesh.flowersalon.domain.service.FloristServiceImpl;
import fedelesh.flowersalon.domain.service.FlowerServiceImpl;
import fedelesh.flowersalon.domain.service.OrderServiceImpl;
import fedelesh.flowersalon.domain.service.SignUpServiceImpl;
import fedelesh.flowersalon.domain.service.SupplierServiceImpl;
import fedelesh.flowersalon.domain.service.SupplyServiceImpl;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import fedelesh.flowersalon.presentation.pages.AuthView;
import fedelesh.flowersalon.presentation.pages.MainMenuView;

public class Main {

    static void main(String[] args) {
        DataContext context = new DataContext();

        AuthService authService = new AuthServiceImpl(context);
        SignUpService signUpService = new SignUpServiceImpl(context);

        BouquetService bouquetService = new BouquetServiceImpl(context);
        SupplierService supplierService = new SupplierServiceImpl(context);
        OrderService orderService = new OrderServiceImpl(context);

        FlowerService flowerService = new FlowerServiceImpl(context, authService);
        AccessoryService accessoryService = new AccessoryServiceImpl(context, authService);
        SupplyService supplyService = new SupplyServiceImpl(context, authService);
        FloristService floristService = new FloristServiceImpl(context, authService);

        AuthView authView = new AuthView(authService);

        if (authView.show()) {
            MainMenuView mainMenuView = new MainMenuView(
                  authService,
                  signUpService,
                  orderService,
                  accessoryService,
                  bouquetService,
                  supplyService,
                  supplierService,
                  floristService,
                  flowerService
            );

            mainMenuView.show();
        } else {
            System.out.println("Програму завершено (вхід не виконано).");
        }
    }
}
