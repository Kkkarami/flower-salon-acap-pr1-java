package fedelesh.flowersalon.domain.specification;

import fedelesh.flowersalon.domain.impl.BouquetFlower;
import java.util.UUID;

public final class BouquetFlowerSpecifications {

    private BouquetFlowerSpecifications() {
    }

    public static Specification<BouquetFlower> byBouquetId(UUID bouquetId) {
        return bf -> bf.getBouquetId() != null
              && bf.getBouquetId().equals(bouquetId);
    }

    public static Specification<BouquetFlower> byFlowerId(UUID flowerId) {
        return bf -> bf.getFlowerId() != null
              && bf.getFlowerId().equals(flowerId);
    }

    public static Specification<BouquetFlower> byBouquetAndFlower(
          UUID bouquetId, UUID flowerId) {
        return byBouquetId(bouquetId)
              .and(byFlowerId(flowerId));
    }

    public static Specification<BouquetFlower> all() {
        return bf -> true;
    }

    public static Specification<BouquetFlower> none() {
        return bf -> false;
    }
}
