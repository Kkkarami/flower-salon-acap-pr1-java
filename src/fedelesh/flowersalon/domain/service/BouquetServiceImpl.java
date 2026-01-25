package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.contract.BouquetService;
import fedelesh.flowersalon.domain.dto.BouquetCreateDto;
import fedelesh.flowersalon.domain.impl.Accessory;
import fedelesh.flowersalon.domain.impl.Bouquet;
import fedelesh.flowersalon.domain.impl.BouquetFlower;
import fedelesh.flowersalon.domain.impl.Flower;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class BouquetServiceImpl implements BouquetService {

    private final DataContext context;

    public BouquetServiceImpl(DataContext context) {
        this.context = context;
    }

    @Override
    public Bouquet create(BouquetCreateDto dto) {
        double totalPrice = 0;

        if (dto.wrapperId() != null) {
            Accessory wrapper = context.accessories().findById(dto.wrapperId())
                  .orElseThrow(() -> new IllegalArgumentException("Упаковку не знайдено"));
            totalPrice += wrapper.getPrice();
        }

        Bouquet bouquet = new Bouquet(dto.name(), 0, "Складений букет");
        context.registerNew(bouquet);

        for (var entry : dto.flowerIdsWithQuantities().entrySet()) {
            Flower flower = context.flowers().findById(entry.getKey())
                  .orElseThrow(() -> new IllegalArgumentException("Квітку не знайдено"));

            if (flower.getQuantity() < entry.getValue()) {
                throw new IllegalStateException(
                      "Недостатньо квітів на складі: " + flower.getName());
            }

            flower.setQuantity(flower.getQuantity() - entry.getValue());
            context.registerDirty(flower);

            BouquetFlower bf = new BouquetFlower(bouquet.getId(), flower.getId(), entry.getValue(),
                  flower.getPrice());
            context.registerNew(bf);

            totalPrice += (flower.getPrice() * entry.getValue());
        }

        bouquet.setPrice(totalPrice);
        context.commit();
        return bouquet;
    }

    @Override
    public Bouquet get(UUID id) {
        return context.bouquets().findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Букет не знайдено"));
    }

    @Override
    public Set<Bouquet> getAll() {
        return context.bouquets().findAll(null).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Bouquet> getAll(Predicate<Bouquet> filter) {
        return context.bouquets().findAll(null).stream().filter(filter).collect(Collectors.toSet());
    }

    @Override
    public Bouquet add(Bouquet entity) {
        context.registerNew(entity);
        context.commit();
        return entity;
    }

    @Override
    public boolean remove(Bouquet entity) {
        context.registerDeleted(entity);
        context.commit();
        return true;
    }
}
