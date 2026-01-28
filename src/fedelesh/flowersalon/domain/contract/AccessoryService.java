package fedelesh.flowersalon.domain.contract;

import fedelesh.flowersalon.domain.Service;
import fedelesh.flowersalon.domain.dto.AccessoryAddDto;
import fedelesh.flowersalon.domain.impl.Accessory;
import java.util.List;
import java.util.UUID;

public interface AccessoryService extends Service<Accessory> {

    Accessory add(AccessoryAddDto dto);

    List<Accessory> getAvailable();

    void toggleAvailability(UUID id);
}
