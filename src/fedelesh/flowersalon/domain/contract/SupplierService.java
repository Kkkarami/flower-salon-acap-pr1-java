package fedelesh.flowersalon.domain.contract;

import fedelesh.flowersalon.domain.Service;
import fedelesh.flowersalon.domain.dto.SupplierCreateDto;
import fedelesh.flowersalon.domain.impl.Supplier;

public interface SupplierService extends Service<Supplier> {

    Supplier add(SupplierCreateDto dto);
}
