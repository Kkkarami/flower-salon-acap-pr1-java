package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.Service;
import fedelesh.flowersalon.domain.impl.OrderItem;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class OrderItemServiceImpl implements Service<OrderItem> {

    private final DataContext context;

    public OrderItemServiceImpl(DataContext context) {
        this.context = context;
    }

    @Override
    public OrderItem get(UUID id) {
        return context.orderItems().findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Позицію замовлення не знайдено"));
    }

    @Override
    public Set<OrderItem> getAll() {
        return context.orderItems().findAll(null).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<OrderItem> getAll(Predicate<OrderItem> filter) {
        return context.orderItems().findAll(null).stream().filter(filter)
              .collect(Collectors.toSet());
    }

    @Override
    public OrderItem add(OrderItem entity) {
        context.registerNew(entity);
        context.commit();
        return entity;
    }

    @Override
    public boolean remove(OrderItem entity) {
        context.registerDeleted(entity);
        context.commit();
        return true;
    }
}
