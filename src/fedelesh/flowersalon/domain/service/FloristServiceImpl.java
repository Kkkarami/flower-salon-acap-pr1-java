package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.contract.AuthService;
import fedelesh.flowersalon.domain.contract.FloristService;
import fedelesh.flowersalon.domain.enums.WorkerRole;
import fedelesh.flowersalon.domain.impl.Florist;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class FloristServiceImpl implements FloristService {

    private final DataContext context;
    private final AuthService authService;

    public FloristServiceImpl(DataContext context, AuthService authService) {
        this.context = context;
        this.authService = authService;
    }

    @Override
    public Florist get(UUID id) {
        return context.florists().findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Працівника не знайдено"));
    }

    @Override
    public Set<Florist> getAll() {
        return context.florists().findAll(null).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Florist> getAll(Predicate<Florist> filter) {
        return context.florists().findAll(null).stream().filter(filter).collect(Collectors.toSet());
    }

    @Override
    public Florist add(Florist entity) {
        if (authService.getUser().getRole() != WorkerRole.OWNER) {
            throw new SecurityException("Лише власник може додавати працівників");
        }
        context.registerNew(entity);
        context.commit();
        return entity;
    }

    @Override
    public boolean remove(Florist entity) {
        if (authService.getUser().getRole() != WorkerRole.OWNER) {
            throw new SecurityException("Лише власник може видаляти працівників");
        }
        context.registerDeleted(entity);
        context.commit();
        return true;
    }
}
