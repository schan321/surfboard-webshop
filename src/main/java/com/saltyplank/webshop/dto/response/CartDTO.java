package com.saltyplank.webshop.dto.response;

import java.util.List;

public class CartDTO {

    private Long orderId;
    private List<CartItemDTO> items;
    private Double total;

    public CartDTO(Long orderId, List<CartItemDTO> items, Double total) {
        this.orderId = orderId;
        this.items = items;
        this.total = total;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public List<CartItemDTO> getItems() { return items; }
    public void setItems(List<CartItemDTO> items) { this.items = items; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}