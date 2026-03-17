package com.tableorder.order.domain;

import jakarta.persistence.*;

@Entity
public class OrderItemOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderItemId;

    @Column(nullable = false)
    private String optionGroupName;

    @Column(nullable = false)
    private String optionName;

    @Column(nullable = false)
    private Integer additionalPrice;

    protected OrderItemOption() {}

    public OrderItemOption(Long orderItemId, String optionGroupName, String optionName, Integer additionalPrice) {
        this.orderItemId = orderItemId;
        this.optionGroupName = optionGroupName;
        this.optionName = optionName;
        this.additionalPrice = additionalPrice;
    }

    public Long getId() { return id; }
    public Long getOrderItemId() { return orderItemId; }
    public String getOptionGroupName() { return optionGroupName; }
    public String getOptionName() { return optionName; }
    public Integer getAdditionalPrice() { return additionalPrice; }
}
