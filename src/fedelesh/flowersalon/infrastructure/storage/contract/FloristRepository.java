package fedelesh.flowersalon.infrastructure.storage.contract;

import fedelesh.flowersalon.domain.impl.Florist;
import fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.Optional;

public interface FloristRepository extends Repository<Florist> {

    /**
     * Знайти флориста за номером телефону (для логіну).
     */
    Optional<Florist> findByPhoneNumber(String phoneNumber);
}
