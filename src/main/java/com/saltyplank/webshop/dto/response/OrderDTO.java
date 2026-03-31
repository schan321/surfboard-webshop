package com.saltyplank.webshop.dto.response;

import com.saltyplank.webshop.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    private Long id;
    private LocalDateTime createdAt;
    private Double total;
    private OrderStatus status;
    private String shippingAddress;
    private List<OrderItemDTO> items;

    public OrderDTO(Long id, LocalDateTime createdAt, Double total,
                    OrderStatus status, String shippingAddress, List<OrderItemDTO> items) {
        this.id = id;
        this.createdAt = createdAt;
        this.total = total;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.items = items;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public List<OrderItemDTO> getItems() { return items; }
    public void setItems(List<OrderItemDTO> items) { this.items = items; }
}
