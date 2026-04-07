package com.saltyplank.webshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import com.saltyplank.webshop.models.Category;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    @Column
    private Integer volume_liter;

    @Column
    private Integer length_cm;


    @Column
    private Integer width_cm;

    @Column
    private String fin_system;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String imageUrl;

    public Product() {
    }

    public Product(Long id, String name, String description, Double price, Integer stock, Integer volume_liter, Integer length_cm, Integer width_cm, String fin_system, Category category, String imageUrl) {
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

    public Product(String name, String description, Double price, Integer stock,
                   Integer volume_liter, Integer length_cm, Integer width_cm,
                   String fin_system, Category category, String imageUrl) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
