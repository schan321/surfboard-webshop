package com.saltyplank.webshop.dto.response;

import com.saltyplank.webshop.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    public Long id;
    public LocalDateTime createdAt;
    public Double total;
    public OrderStatus status;
    public String street;
    public String postalCode;
    public String city;
    public String country;
    public List<OrderItemDTO> items;

    public OrderDTO(Long id, LocalDateTime createdAt, Double total, OrderStatus status, String street, String postalCode, String city, String country, List<OrderItemDTO> items) {
        this.id = id;
        this.createdAt = createdAt;
        this.total = total;
        this.status = status;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
