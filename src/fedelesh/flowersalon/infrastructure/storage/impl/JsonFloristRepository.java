package fedelesh.flowersalon.infrastructure.storage.impl;

import com.google.gson.reflect.TypeToken;
import fedelesh.flowersalon.domain.impl.Florist;
import fedelesh.flowersalon.infrastructure.storage.JsonFilePath;
import fedelesh.flowersalon.infrastructure.storage.JsonRepository;
import fedelesh.flowersalon.infrastructure.storage.contract.FloristRepository;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

class JsonFloristRepository extends JsonRepository<Florist> implements FloristRepository {

    private static final Type LIST_TYPE = new TypeToken<List<Florist>>() {
    }.getType();

    public JsonFloristRepository() {
        super(JsonFilePath.FLORISTS.getPath(), LIST_TYPE);
    }

    @Override
    public Optional<Florist> findByPhoneNumber(String phoneNumber) {
        return findAllInternal().stream()
              .filter(f -> f.getPhoneNumber().equals(phoneNumber))
              .findFirst();
    }
}
