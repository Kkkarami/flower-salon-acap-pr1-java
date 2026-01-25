package fedelesh.flowersalon.infrastructure.storage.impl;

import com.google.gson.reflect.TypeToken;
import fedelesh.flowersalon.domain.impl.Bouquet;
import fedelesh.flowersalon.infrastructure.storage.JsonFilePath;
import fedelesh.flowersalon.infrastructure.storage.JsonRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.BouquetRepository;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

class JsonBouquetRepository extends JsonRepository<Bouquet> implements BouquetRepository {

    private static final Type LIST_TYPE = new TypeToken<List<Bouquet>>() {
    }.getType();

    public JsonBouquetRepository() {
        super(JsonFilePath.BOUQUETS.getPath(), LIST_TYPE);
    }

    @Override
    public Optional<Bouquet> findByName(String name) {
        return findAllInternal().stream()
              .filter(b -> b.getName().equalsIgnoreCase(name))
              .findFirst();
    }
}
