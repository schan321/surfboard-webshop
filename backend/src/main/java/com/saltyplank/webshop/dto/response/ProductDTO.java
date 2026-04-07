package com.saltyplank.webshop.dto.response;

import jakarta.persistence.Column;

public class ProductDTO {

    public Long id;
    public String name;
    public String description;
    public Double price;
    public Integer stock;
    public Integer volume_liter;
    public Integer length_cm;
    public Integer width_cm;
    public String fin_system;
    public CategoryResponse category;
    public String imageUrl;


    public ProductDTO(Long id, String name, String description, Double price, Integer stock, Integer volume_liter, Integer length_cm, Integer width_cm, String fin_system, CategoryResponse category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.volume_liter = volume_liter;
        this.length_cm = length_cm;
        this.width_cm = width_cm;
        this.fin_system = fin_system;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public CategoryResponse getCategory() { return category; }
    public void setCategory(CategoryResponse category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}