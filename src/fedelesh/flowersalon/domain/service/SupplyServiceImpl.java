package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.contract.AuthService;
import fedelesh.flowersalon.domain.contract.SupplyService;
import fedelesh.flowersalon.domain.dto.SupplyDto;
import fedelesh.flowersalon.domain.enums.WorkerRole;
import fedelesh.flowersalon.domain.impl.Flower;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;

public final class SupplyServiceImpl implements SupplyService {

    private final DataContext context;
    private final AuthService authService;

    public SupplyServiceImpl(DataContext context, AuthService authService) {
        this.context = context;
        this.authService = authService;
    }

    @Override
    public void processSupply(SupplyDto dto) {
        if (authService.getUser().getRole() != WorkerRole.OWNER) {
            throw new SecurityException("Тільки власник може приймати поставки");
        }

        Flower flower = context.flowers().findById(dto.flowerId())
              .orElseThrow(() -> new IllegalArgumentException("Товар не знайдено"));

        flower.setQuantity(flower.getQuantity() + dto.quantity());

        context.registerDirty(flower);
        context.commit();
    }
}
