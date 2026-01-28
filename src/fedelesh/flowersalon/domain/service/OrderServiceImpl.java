package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.contract.OrderService;
import fedelesh.flowersalon.domain.dto.OrderCreateDto;
import fedelesh.flowersalon.domain.enums.OrderStatus;
import fedelesh.flowersalon.domain.impl.Flower;
import fedelesh.flowersalon.domain.impl.Order;
import fedelesh.flowersalon.domain.impl.OrderItem;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class OrderServiceImpl implements OrderService {

    private final DataContext context;

    public OrderServiceImpl(DataContext context) {
        this.context = context;
    }

    @Override
    public void updateStatus(UUID orderId, OrderStatus newStatus) {
        Order order = context.orders().findById(orderId)
              .orElseThrow(() -> new IllegalArgumentException("Замовлення не знайдено"));

        order.setStatus(newStatus);

        // Позначаємо об'єкт як змінений та зберігаємо
        context.registerDirty(order);
        context.commit();
    }

    @Override
    public Order create(OrderCreateDto dto) {
        Order order = new Order(LocalDateTime.now(), 0, dto.workerId(), OrderStatus.NEW);
        context.registerNew(order);

        double finalTotal = 0;

        for (var entry : dto.items().entrySet()) {
            UUID itemId = entry.getKey();
            int quantity = entry.getValue();
            double price = 0;

            var flowerOpt = context.flowers().findById(itemId);
            if (flowerOpt.isPresent()) {
                Flower flower = flowerOpt.get();
                if (flower.getQuantity() < quantity) {
                    throw new IllegalStateException("Недостатньо квіток: " + flower.getName());
                }
                price = flower.getPrice();
                flower.setQuantity(flower.getQuantity() - quantity);
                context.registerDirty(flower);
            } else {
                var bouquetOpt = context.bouquets().findById(itemId);
                if (bouquetOpt.isPresent()) {
                    var bouquet = bouquetOpt.get();
                    price = bouquetOpt.get().getPrice();
                    context.registerDeleted(bouquet);
                } else {
                    var accessoryOpt = context.accessories().findById(itemId);
                    if (accessoryOpt.isPresent()) {
                        var accessory = accessoryOpt.get();
                        if (!accessory.isAvailable()) {
                            throw new IllegalStateException(
                                  "Аксесуар недоступний: " + accessory.getName());
                        }
                        price = accessory.getPrice();
                    } else {
                        throw new IllegalArgumentException(
                              "Товар з ID " + itemId
                                    + " не знайдено ні в квітах, ні в букетах, ні в аксесуарах");
                    }
                }
            }

            OrderItem item = new OrderItem(order.getId(), itemId, quantity, price);
            context.registerNew(item);

            finalTotal += (price * quantity);
        }

        order.setTotalPrice(finalTotal);
        context.commit();
        return order;
    }

    @Override
    public Order get(UUID id) {
        return context.orders().findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Замовлення не знайдено"));
    }

    @Override
    public Set<Order> getAll() {
        return context.orders().findAll(null).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Order> getAll(Predicate<Order> filter) {
        return context.orders().findAll(null).stream().filter(filter).collect(Collectors.toSet());
    }

    @Override
    public Order add(Order entity) {
        context.registerNew(entity);
        context.commit();
        return entity;
    }

    @Override
    public boolean remove(Order entity) {
        context.registerDeleted(entity);
        context.commit();
        return true;
    }
}
