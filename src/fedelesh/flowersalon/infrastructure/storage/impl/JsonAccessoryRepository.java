package fedelesh.flowersalon.infrastructure.storage.impl;

import com.google.gson.reflect.TypeToken;
import fedelesh.flowersalon.domain.impl.Accessory;
import fedelesh.flowersalon.infrastructure.storage.JsonFilePath;
import fedelesh.flowersalon.infrastructure.storage.JsonRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.AccessoryRepository;
import java.lang.reflect.Type;
import java.util.List;

class JsonAccessoryRepository extends JsonRepository<Accessory> implements AccessoryRepository {

    private static final Type LIST_TYPE = new TypeToken<List<Accessory>>() {
    }.getType();

    public JsonAccessoryRepository() {
        super(JsonFilePath.ACCESSORIES.getPath(), LIST_TYPE);
    }
}
