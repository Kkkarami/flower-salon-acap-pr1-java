package fedelesh.flowersalon.infrastructure.storage.contract;

import fedelesh.flowersalon.domain.impl.Supplier;
import fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.Optional;

public interface SupplierRepository extends Repository<Supplier> {

    /**
     * Знайти постачальника за назвою компанії.
     */
    Optional<Supplier> findByCompanyName(String companyName);
}
