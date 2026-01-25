package fedelesh.flowersalon.domain.contract;

import fedelesh.flowersalon.domain.Service;
import fedelesh.flowersalon.domain.dto.OrderCreateDto;
import fedelesh.flowersalon.domain.impl.Order;

public interface OrderService extends Service<Order> {

    Order create(OrderCreateDto dto);
}
