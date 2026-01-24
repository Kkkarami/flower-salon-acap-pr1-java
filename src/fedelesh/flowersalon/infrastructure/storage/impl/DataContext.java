package fedelesh.flowersalon.infrastructure.storage.impl;

import fedelesh.flowersalon.domain.Entity;
import fedelesh.flowersalon.infrastructure.storage.Repository;
import fedelesh.flowersalon.infrastructure.storage.contract.AccessoryRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.BouquetFlowerRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.FloristRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.FlowerRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.OrderItemRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.OrderRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.SupplierRepository;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DataContext {


    private final AccessoryRepository accessoryRepository;
    private final BouquetFlowerRepository bouquetFlowerRepository;
    private final FloristRepository floristRepository;
    private final FlowerRepository flowerRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final SupplierRepository supplierRepository;
    private final Set<Entity> newEntities = new LinkedHashSet<>();
    private final Set<Entity> dirtyEntities = new LinkedHashSet<>();
    private final Map<Repository<? extends Entity>, Set<UUID>> deletedIdsMap = new HashMap<>();
    private DataContext() {
        this.accessoryRepository = new JsonAccessoryRepository();
        this.bouquetFlowerRepository = new JsonBouquetFlowerRepository();
        this.floristRepository = new JsonFloristRepository();
        this.flowerRepository = new JsonFlowerRepository();
        this.orderItemRepository = new JsonOrderItemRepository();
        this.orderRepository = new JsonOrderRepository();
        this.supplierRepository = new JsonSupplierRepository();
    }

    public static DataContext getInstance() {
        return Holder.INSTANCE;
    }

    public AccessoryRepository accessories() {
        return accessoryRepository;
    }

    public BouquetFlowerRepository bouquetFlowers() {
        return bouquetFlowerRepository;
    }

    public FloristRepository florists() {
        return floristRepository;
    }

    public FlowerRepository flowers() {
        return flowerRepository;
    }

    public OrderItemRepository orderItems() {
        return orderItemRepository;
    }

    public OrderRepository orders() {
        return orderRepository;
    }

    public SupplierRepository suppliers() {
        return supplierRepository;
    }

    public <T extends Entity> void registerNew(T entity) {
        removeFromDeleted(entity);
        dirtyEntities.remove(entity);
        newEntities.add(entity);
    }

    public <T extends Entity> void registerDirty(T entity) {
        if (!newEntities.contains(entity) && !isDeleted(entity)) {
            dirtyEntities.add(entity);
        }
    }

    public <T extends Entity> void registerDeleted(T entity) {
        if (newEntities.remove(entity)) {
            return;
        }
        dirtyEntities.remove(entity);
        addToDeleted(entity);
    }

    public <T extends Entity> void registerDeletedById(Repository<T> repository, UUID id) {
        deletedIdsMap.computeIfAbsent(repository, k -> new LinkedHashSet<>()).add(id);
    }

    @SuppressWarnings("unchecked")
    public void commit() {
        for (Entity entity : newEntities) {
            Repository<Entity> repo = getRepositoryForEntity(entity);
            if (repo != null) {
                repo.save(entity);
            }
        }

        for (Entity entity : dirtyEntities) {
            Repository<Entity> repo = getRepositoryForEntity(entity);
            if (repo != null) {
                repo.save(entity);
            }
        }

        for (Map.Entry<Repository<? extends Entity>, Set<UUID>> entry : deletedIdsMap.entrySet()) {
            Repository<Entity> repo = (Repository<Entity>) entry.getKey();
            for (UUID id : entry.getValue()) {
                repo.deleteById(id);
            }
        }

        clear();
    }

    public void rollback() {
        clear();
    }

    public void clear() {
        newEntities.clear();
        dirtyEntities.clear();
        deletedIdsMap.clear();
    }

    public boolean hasChanges() {
        return !newEntities.isEmpty() || !dirtyEntities.isEmpty() || !deletedIdsMap.isEmpty();
    }

    public String getChangesSummary() {
        int deletedCount = deletedIdsMap.values().stream().mapToInt(Set::size).sum();
        return String.format("New: %d, Dirty: %d, Deleted: %d",
              newEntities.size(), dirtyEntities.size(), deletedCount);
    }

    @SuppressWarnings("unchecked")
    private <T extends Entity> Repository<T> getRepositoryForEntity(T entity) {
        String className = entity.getClass().getSimpleName();

        return switch (className) {
            case "Accessory" -> (Repository<T>) accessoryRepository;
            case "BouquetFlower" -> (Repository<T>) bouquetFlowerRepository;
            case "Florist" -> (Repository<T>) floristRepository;
            case "Flower" -> (Repository<T>) flowerRepository;
            case "OrderItem" -> (Repository<T>) orderItemRepository;
            case "Order" -> (Repository<T>) orderRepository;
            case "Supplier" -> (Repository<T>) supplierRepository;
            default -> null;
        };
    }

    private void addToDeleted(Entity entity) {
        Repository<? extends Entity> repo = getRepositoryForEntity(entity);
        if (repo != null) {
            deletedIdsMap.computeIfAbsent(repo, k -> new LinkedHashSet<>()).add(entity.getId());
        }
    }

    private void removeFromDeleted(Entity entity) {
        Repository<? extends Entity> repo = getRepositoryForEntity(entity);
        if (repo != null) {
            Set<UUID> ids = deletedIdsMap.get(repo);
            if (ids != null) {
                ids.remove(entity.getId());
            }
        }
    }

    private boolean isDeleted(Entity entity) {
        Repository<? extends Entity> repo = getRepositoryForEntity(entity);
        if (repo != null) {
            Set<UUID> ids = deletedIdsMap.get(repo);
            return ids != null && ids.contains(entity.getId());
        }
        return false;
    }

    private static class Holder {

        private static final DataContext INSTANCE = new DataContext();
    }
}
