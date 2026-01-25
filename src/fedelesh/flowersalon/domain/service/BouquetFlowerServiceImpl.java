package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.Service;
import fedelesh.flowersalon.domain.impl.BouquetFlower;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class BouquetFlowerServiceImpl implements Service<BouquetFlower> {

    private final DataContext context;

    public BouquetFlowerServiceImpl(DataContext context) {
        this.context = context;
    }

    @Override
    public BouquetFlower get(UUID id) {
        return context.bouquetFlowers().findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Зв'язок букет-квітка не знайдено"));
    }

    @Override
    public Set<BouquetFlower> getAll() {
        return context.bouquetFlowers().findAll(null).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<BouquetFlower> getAll(Predicate<BouquetFlower> filter) {
        return context.bouquetFlowers().findAll(null).stream().filter(filter)
              .collect(Collectors.toSet());
    }

    @Override
    public BouquetFlower add(BouquetFlower entity) {
        context.registerNew(entity);
        context.commit();
        return entity;
    }

    @Override
    public boolean remove(BouquetFlower entity) {
        context.registerDeleted(entity);
        context.commit();
        return true;
    }
}
