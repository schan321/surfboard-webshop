package com.saltyplank.webshop.dto.request;

import jakarta.validation.constraints.*;

public class ProductRequest {

    @NotBlank
    @Size(min = 2, max = 100)
    public String name;

    @Size(max = 1000)
    public String description;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("9999.99")
    public Double price;

    @NotNull
    @Min(0)
    @Max(9999)
    public Integer stock;

    public Integer volume_liter;

    public Integer length_cm;

    public Integer width_cm;

    public String fin_system;

    @NotNull
    public Long categoryId;

    @Size(max = 255)
    public String imageUrl;

    public Integer getVolume_liter() {
        return volume_liter;
    }

    public void setVolume_liter(Integer volume_liter) {
        this.volume_liter = volume_liter;
    }

    public Integer getLength_cm() {
        return length_cm;
    }

    public void setLength_cm(Integer length_cm) {
        this.length_cm = length_cm;
    }

    public Integer getWidth_cm() {
        return width_cm;
    }

    public void setWidth_cm(Integer width_cm) {
        this.width_cm = width_cm;
    }

    public String getFin_system() {
        return fin_system;
    }

    public void setFin_system(String fin_system) {
        this.fin_system = fin_system;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}