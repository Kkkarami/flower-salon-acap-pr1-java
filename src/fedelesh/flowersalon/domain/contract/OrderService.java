package fedelesh.flowersalon.domain.contract;

import fedelesh.flowersalon.domain.Service;
import fedelesh.flowersalon.domain.dto.OrderCreateDto;
import fedelesh.flowersalon.domain.enums.OrderStatus;
import fedelesh.flowersalon.domain.impl.Order;
import java.util.UUID;

public interface OrderService extends Service<Order> {

    Order create(OrderCreateDto dto);

    void updateStatus(UUID orderId, OrderStatus newStatus);
}
