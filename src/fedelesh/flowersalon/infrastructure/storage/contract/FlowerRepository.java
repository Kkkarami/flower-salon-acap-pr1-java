package fedelesh.flowersalon.infrastructure.storage.contract;

import fedelesh.flowersalon.domain.impl.Flower;
import fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.List;
import java.util.UUID;

public interface FlowerRepository extends Repository<Flower> {

    /**
     * Отримати всі квіти конкретного постачальника.
     */
    List<Flower> findBySupplierId(UUID supplierId);

    /**
     * Видалити всі квіти постачальника (каскад).
     */
    void deleteBySupplierId(UUID supplierId);
}
