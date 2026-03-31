package com.saltyplank.webshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Double unitPrice; // price at time of ordering

    @Column(nullable = false)
    private Integer quantity;

    public OrderItem() {
    }

    public OrderItem(Long id, Order order, Long productId, String productName, Double productPrice, Integer quantity) {
        this.id = id;
        this.order = order;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}