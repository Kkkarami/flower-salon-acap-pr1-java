package fedelesh.flowersalon.infrastructure.storage.impl;

import com.google.gson.reflect.TypeToken;
import fedelesh.flowersalon.domain.impl.Supplier;
import fedelesh.flowersalon.infrastructure.storage.JsonFilePath;
import fedelesh.flowersalon.infrastructure.storage.JsonRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.SupplierRepository;
import java.lang.reflect.Type;
import java.util.List;

class JsonSupplierRepository extends JsonRepository<Supplier> implements SupplierRepository {

    private static final Type LIST_TYPE = new TypeToken<List<Supplier>>() {
    }.getType();

    public JsonSupplierRepository() {
        super(JsonFilePath.SUPPLIERS.getPath(), LIST_TYPE);
    }
}
