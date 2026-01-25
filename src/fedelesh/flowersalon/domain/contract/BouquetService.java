package fedelesh.flowersalon.domain.contract;

import fedelesh.flowersalon.domain.Service;
import fedelesh.flowersalon.domain.dto.BouquetCreateDto;
import fedelesh.flowersalon.domain.impl.Bouquet;

public interface BouquetService extends Service<Bouquet> {

    Bouquet create(BouquetCreateDto dto);
}
