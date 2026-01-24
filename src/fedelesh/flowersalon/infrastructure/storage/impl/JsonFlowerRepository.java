package fedelesh.flowersalon.infrastructure.storage.impl;

import com.google.gson.reflect.TypeToken;
import fedelesh.flowersalon.domain.impl.Flower;
import fedelesh.flowersalon.infrastructure.storage.JsonFilePath;
import fedelesh.flowersalon.infrastructure.storage.JsonRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.FlowerRepository;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

class JsonFlowerRepository extends JsonRepository<Flower> implements FlowerRepository {

    private static final Type LIST_TYPE = new TypeToken<List<Flower>>() {
    }.getType();

    public JsonFlowerRepository() {
        super(JsonFilePath.FLOWERS.getPath(), LIST_TYPE);
    }

    @Override
    public List<Flower> findBySupplierId(UUID supplierId) {
        return findAllInternal().stream()
              .filter(f -> supplierId.equals(f.getSupplierId()))
              .toList();
    }

    @Override
    public void deleteBySupplierId(UUID supplierId) {
        List<Flower> entities = findAllInternal();
        List<Flower> toKeep = entities.stream()
              .filter(f -> !supplierId.equals(f.getSupplierId()))
              .toList();

        if (toKeep.size() < entities.size()) {
            invalidateCache();
            writeToFile(toKeep);
        }
    }
}