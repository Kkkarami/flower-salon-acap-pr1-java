package fedelesh.flowersalon.infrastructure.storage.contract;

import fedelesh.flowersalon.domain.impl.Supplier;
import java.util.Optional;

public interface SupplierRepository {

    /**
     * Знайти постачальника за назвою компанії.
     */
    Optional<Supplier> findByCompanyName(String companyName);
}
