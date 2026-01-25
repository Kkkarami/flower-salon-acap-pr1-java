package fedelesh.flowersalon.domain.contract;

import fedelesh.flowersalon.domain.Service;
import fedelesh.flowersalon.domain.dto.AccessoryAddDto;
import fedelesh.flowersalon.domain.impl.Accessory;

public interface AccessoryService extends Service<Accessory> {

    Accessory add(AccessoryAddDto dto);
}
