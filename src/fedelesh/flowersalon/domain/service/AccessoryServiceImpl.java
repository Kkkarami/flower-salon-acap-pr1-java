package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.contract.AccessoryService;
import fedelesh.flowersalon.domain.contract.AuthService;
import fedelesh.flowersalon.domain.dto.AccessoryAddDto;
import fedelesh.flowersalon.domain.enums.WorkerRole;
import fedelesh.flowersalon.domain.impl.Accessory;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class AccessoryServiceImpl implements AccessoryService {

    private final DataContext context;
    private final AuthService authService;

    public AccessoryServiceImpl(DataContext context, AuthService authService) {
        this.context = context;
        this.authService = authService;
    }

    @Override
    public Accessory add(AccessoryAddDto dto) {
        if (authService.getUser().getRole() != WorkerRole.OWNER) {
            throw new SecurityException("Лише власник може додавати аксесуари");
        }
        Accessory accessory = new Accessory(dto.name(), dto.price(), dto.description(),
              dto.available());
        context.registerNew(accessory);
        context.commit();
        return accessory;
    }

    @Override
    public void toggleAvailability(UUID id) {
        Accessory accessory = context.accessories().findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Аксесуар не знайдено"));

        accessory.setAvailable(!accessory.isAvailable());
        context.registerDirty(accessory);
        context.commit();
    }

    @Override
    public List<Accessory> getAvailable() {
        return context.accessories().findAll(null).stream()
              .filter(Accessory::isAvailable)
              .collect(Collectors.toList());
    }

    @Override
    public Accessory get(UUID id) {
        return context.accessories().findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Аксесуар не знайдено"));
    }

    @Override
    public Set<Accessory> getAll() {
        return context.accessories().findAll(null).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Accessory> getAll(Predicate<Accessory> filter) {
        return context.accessories().findAll(null).stream().filter(filter)
              .collect(Collectors.toSet());
    }

    @Override
    public Accessory add(Accessory entity) {
        context.registerNew(entity);
        context.commit();
        return entity;
    }

    @Override
    public boolean remove(Accessory entity) {
        context.registerDeleted(entity);
        context.commit();
        return true;
    }
}
