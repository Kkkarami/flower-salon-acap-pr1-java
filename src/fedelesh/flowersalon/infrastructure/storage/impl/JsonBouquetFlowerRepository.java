package fedelesh.flowersalon.infrastructure.storage.impl;

import com.google.gson.reflect.TypeToken;
import fedelesh.flowersalon.domain.impl.BouquetFlower;
import fedelesh.flowersalon.infrastructure.storage.JsonFilePath;
import fedelesh.flowersalon.infrastructure.storage.JsonRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.BouquetFlowerRepository;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class JsonBouquetFlowerRepository extends JsonRepository<BouquetFlower> implements
      BouquetFlowerRepository {

    private static final Type LIST_TYPE = new TypeToken<List<BouquetFlower>>() {
    }.getType();

    public JsonBouquetFlowerRepository() {
        super(JsonFilePath.BOUQUET_FLOWER.getPath(), LIST_TYPE);
    }

    @Override
    public void attach(UUID flowerId, UUID bouquetId, int quantity, double priceAtCreation) {
        List<BouquetFlower> entities = findAllInternal();

        boolean exists = entities.stream()
              .anyMatch(
                    bf -> bf.getFlowerId().equals(flowerId) && bf.getBouquetId().equals(bouquetId));

        if (!exists) {
            save(new BouquetFlower(bouquetId, flowerId, quantity, priceAtCreation));
        }
    }

    @Override
    public void detach(UUID flowerId, UUID bouquetId) {
        List<BouquetFlower> entities = findAllInternal();
        List<BouquetFlower> toKeep = entities.stream()
              .filter(bf -> !(bf.getFlowerId().equals(flowerId) && bf.getBouquetId()
                    .equals(bouquetId)))
              .toList();

        if (toKeep.size() < entities.size()) {
            invalidateCache();
            writeToFile(toKeep);
        }
    }

    @Override
    public void sync(UUID bouquetId, List<BouquetFlower> items) {
        List<BouquetFlower> entities = findAllInternal();

        List<BouquetFlower> withoutBouquet = entities.stream()
              .filter(bf -> !bf.getBouquetId().equals(bouquetId))
              .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        withoutBouquet.addAll(items);

        invalidateCache();
        writeToFile(withoutBouquet);
    }

    @Override
    public void clearByBouquetId(UUID bouquetId) {
        List<BouquetFlower> entities = findAllInternal();
        List<BouquetFlower> toKeep = entities.stream()
              .filter(bf -> !bf.getBouquetId().equals(bouquetId))
              .toList();

        if (toKeep.size() < entities.size()) {
            invalidateCache();
            writeToFile(toKeep);
        }
    }

    @Override
    public void detachFlowerFromAllBouquets(UUID flowerId) {
        List<BouquetFlower> entities = findAllInternal();
        List<BouquetFlower> toKeep = entities.stream()
              .filter(bf -> !bf.getFlowerId().equals(flowerId))
              .toList();

        if (toKeep.size() < entities.size()) {
            invalidateCache();
            writeToFile(toKeep);
        }
    }
}