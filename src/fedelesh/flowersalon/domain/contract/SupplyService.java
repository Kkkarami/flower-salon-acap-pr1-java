package fedelesh.flowersalon.domain.contract;

import java.util.UUID;

public interface SupplyService {

    void processSupply(String name, String desc, double price, int qty, UUID supplierId);
}
