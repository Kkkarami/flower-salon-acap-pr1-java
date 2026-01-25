package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.contract.AuthService;
import fedelesh.flowersalon.domain.contract.FlowerService;
import fedelesh.flowersalon.domain.enums.WorkerRole;
import fedelesh.flowersalon.domain.impl.Flower;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class FlowerServiceImpl implements FlowerService {

    private final DataContext context;
    private final AuthService authService;

    public FlowerServiceImpl(DataContext context, AuthService authService) {
        this.context = context;
        this.authService = authService;
    }

    @Override
    public Flower get(UUID id) {
        return context.flowers().findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Товар не знайдено"));
    }

    @Override
    public Set<Flower> getAll() {
        return context.flowers().findAll(null).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Flower> getAll(Predicate<Flower> filter) {
        return context.flowers().findAll(null).stream().filter(filter).collect(Collectors.toSet());
    }

    @Override
    public Flower add(Flower entity) {
        if (authService.getUser().getRole() != WorkerRole.OWNER) {
            throw new SecurityException("Тільки власник може змінювати асортимент");
        }
        context.registerNew(entity);
        context.commit();
        return entity;
    }

    @Override
    public boolean remove(Flower entity) {
        if (authService.getUser().getRole() != WorkerRole.OWNER) {
            throw new SecurityException("Тільки власник може видаляти товари");
        }
        context.registerDeleted(entity);
        context.commit();
        return true;
    }
}
