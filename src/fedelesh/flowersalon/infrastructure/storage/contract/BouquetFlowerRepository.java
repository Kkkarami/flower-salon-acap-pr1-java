package fedelesh.flowersalon.infrastructure.storage.contract;

import fedelesh.flowersalon.domain.impl.BouquetFlower;
import fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.List;
import java.util.UUID;

public interface BouquetFlowerRepository extends Repository<BouquetFlower> {

    /**
     * Додати квітку до букета.
     */
    void attach(UUID flowerId, UUID bouquetId, int quantity, double priceAtCreation);

    /**
     * Видалити квітку з букета.
     */
    void detach(UUID flowerId, UUID bouquetId);

    /**
     * Оновити склад квітів у букеті (синхронізація).
     */
    void sync(UUID bouquetId, List<BouquetFlower> items);

    /**
     * Видалити всі квіти з конкретного букета.
     */
    void clearByBouquetId(UUID bouquetId);

    /**
     * Видалити згадки про конкретну квітку з усіх букетів.
     */
    void detachFlowerFromAllBouquets(UUID flowerId);
}
