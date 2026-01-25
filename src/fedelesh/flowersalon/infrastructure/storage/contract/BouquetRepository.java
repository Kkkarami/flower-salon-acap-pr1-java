package fedelesh.flowersalon.infrastructure.storage.contract;

import fedelesh.flowersalon.domain.impl.Bouquet;
import fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.Optional;

public interface BouquetRepository extends Repository<Bouquet> {

    Optional<Bouquet> findByName(String name);
}
