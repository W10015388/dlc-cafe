package com.tableorder.menu.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 500)
    private String description;

    @Column(length = 255)
    private String imageUrl;

    @Column(nullable = false)
    private Integer displayOrder = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "menuId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OptionGroup> optionGroups = new ArrayList<>();

    protected Menu() {}

    public Menu(Long categoryId, String name, Integer price, String description, Integer displayOrder) {
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.displayOrder = displayOrder;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getCategoryId() { return categoryId; }
    public String getName() { return name; }
    public Integer getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public Integer getDisplayOrder() { return displayOrder; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<OptionGroup> getOptionGroups() { return optionGroups; }

    public void update(Long categoryId, String name, Integer price, String description, Integer displayOrder) {
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.displayOrder = displayOrder;
    }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
