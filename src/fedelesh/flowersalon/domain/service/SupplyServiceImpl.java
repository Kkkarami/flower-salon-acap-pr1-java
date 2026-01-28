package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.contract.AuthService;
import fedelesh.flowersalon.domain.contract.SupplyService;
import fedelesh.flowersalon.domain.enums.WorkerRole;
import fedelesh.flowersalon.domain.impl.Flower;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import java.util.UUID;

public final class SupplyServiceImpl implements SupplyService {

    private final DataContext context;
    private final AuthService authService;

    public SupplyServiceImpl(DataContext context, AuthService authService) {
        this.context = context;
        this.authService = authService;
    }

    @Override
    public void processSupply(String name, String desc, double price, int qty, UUID supplierId) {
        checkOwnerRights();

        Flower flower = context.flowers().findAll(null).stream()
              .filter(f -> f.getName().equalsIgnoreCase(name))
              .findFirst()
              .orElse(null);

        if (flower != null) {
            flower.setQuantity(flower.getQuantity() + qty);
            context.registerDirty(flower);
        } else {
            Flower newFlower = new Flower(
                  name,
                  desc,
                  price,
                  qty,
                  supplierId
            );
            context.registerNew(newFlower);
        }

        context.commit();
    }

    private void checkOwnerRights() {
        if (authService.getUser().getRole() != WorkerRole.OWNER) {
            throw new SecurityException("Тільки власник може здійснювати ці операції");
        }
    }
}
