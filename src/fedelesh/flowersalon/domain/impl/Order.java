package fedelesh.flowersalon.domain.impl;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.enums.OrderStatus;
import fedelesh.flowersalon.domain.exception.EntityValidationException;
import fedelesh.flowersalon.domain.util.ValidationError;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order extends BaseEntity {

    public static final String FIELD_DATE = "date";
    public static final String FIELD_TOTAL = "totalPrice";
    public static final String FIELD_STATUS = "status";

    private UUID workerId;
    private LocalDateTime date;
    private double totalPrice;
    private OrderStatus status;
    private List<OrderItem> items;

    private Order() {
        super();
    }

    public Order(LocalDateTime date, double totalPrice,
          UUID workerId, OrderStatus status) {
        this();
        setDate(date);
        setTotalPrice(totalPrice);
        setStatus(status);
        this.workerId = workerId;
        this.items = new ArrayList<>();
        if (!isValid()) {
            throw new EntityValidationException(getErrors());
        }
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        clearError(FIELD_DATE);
        if (date == null) {
            addError(FIELD_DATE, ValidationError.ORDER_DATE_EMPTY.message());
        }
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        clearError(FIELD_TOTAL);
        if (totalPrice < 0) {
            addError(FIELD_TOTAL, ValidationError.ORDER_TOTAL_INVALID.message());
        }
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        clearError(FIELD_STATUS);
        if (status == null) {
            addError(FIELD_STATUS, ValidationError.ORDER_STATUS_EMPTY.message());
        }
        this.status = status;
    }

    public UUID getWorkerId() {
        return workerId;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f %s", date, totalPrice, status);
    }
}
