package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.contract.SupplierService;
import fedelesh.flowersalon.domain.dto.SupplierCreateDto;
import fedelesh.flowersalon.domain.impl.Supplier;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class SupplierServiceImpl implements SupplierService {

    private final DataContext context;

    public SupplierServiceImpl(DataContext context) {
        this.context = context;
    }

    @Override
    public Supplier add(SupplierCreateDto dto) {
        Supplier supplier = new Supplier(
              dto.companyName(),
              dto.contactPerson(),
              dto.phoneNumber()
        );

        context.registerNew(supplier);
        context.commit();
        return supplier;
    }

    @Override
    public Supplier get(UUID id) {
        return context.suppliers().findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Постачальника не знайдено"));
    }

    @Override
    public Set<Supplier> getAll() {
        return context.suppliers().findAll(null).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Supplier> getAll(Predicate<Supplier> filter) {
        return context.suppliers().findAll(null).stream().filter(filter)
              .collect(Collectors.toSet());
    }

    // Цей метод можна залишити для внутрішніх потреб або видалити,
    // якщо слідувати суворому правилу "тільки DTO ззовні"
    @Override
    public Supplier add(Supplier entity) {
        context.registerNew(entity);
        context.commit();
        return entity;
    }

    @Override
    public boolean remove(Supplier entity) {
        context.registerDeleted(entity);
        context.commit();
        return true;
    }
}
