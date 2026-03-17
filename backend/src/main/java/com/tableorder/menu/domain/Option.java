package com.tableorder.menu.domain;

import jakarta.persistence.*;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long optionGroupId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer additionalPrice = 0;

    @Column(nullable = false)
    private Integer displayOrder = 0;

    protected Option() {}

    public Option(Long optionGroupId, String name, Integer additionalPrice, Integer displayOrder) {
        this.optionGroupId = optionGroupId;
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.displayOrder = displayOrder;
    }

    public Long getId() { return id; }
    public Long getOptionGroupId() { return optionGroupId; }
    public String getName() { return name; }
    public Integer getAdditionalPrice() { return additionalPrice; }
    public Integer getDisplayOrder() { return displayOrder; }

    public void update(String name, Integer additionalPrice, Integer displayOrder) {
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.displayOrder = displayOrder;
    }
}
