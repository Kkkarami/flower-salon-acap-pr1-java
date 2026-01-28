package fedelesh.flowersalon.infrastructure.storage.impl;

import com.google.gson.reflect.TypeToken;
import fedelesh.flowersalon.domain.impl.Flower;
import fedelesh.flowersalon.infrastructure.storage.JsonFilePath;
import fedelesh.flowersalon.infrastructure.storage.JsonRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.FlowerRepository;
import java.lang.reflect.Type;
import java.util.List;

class JsonFlowerRepository extends JsonRepository<Flower> implements FlowerRepository {

    private static final Type LIST_TYPE = new TypeToken<List<Flower>>() {
    }.getType();

    public JsonFlowerRepository() {
        super(JsonFilePath.FLOWERS.getPath(), LIST_TYPE);
    }
}