package com.tableorder.orderhistory.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tableId;

    @Column(nullable = false)
    private Long sessionId;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String orderData;

    @Column(nullable = false)
    private Integer totalAmount;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    protected OrderHistory() {}

    public OrderHistory(Long tableId, Long sessionId, String orderNumber, String orderData,
                        Integer totalAmount, LocalDateTime orderedAt, LocalDateTime completedAt) {
        this.tableId = tableId;
        this.sessionId = sessionId;
        this.orderNumber = orderNumber;
        this.orderData = orderData;
        this.totalAmount = totalAmount;
        this.orderedAt = orderedAt;
        this.completedAt = completedAt;
    }

    public Long getId() { return id; }
    public Long getTableId() { return tableId; }
    public Long getSessionId() { return sessionId; }
    public String getOrderNumber() { return orderNumber; }
    public String getOrderData() { return orderData; }
    public Integer getTotalAmount() { return totalAmount; }
    public LocalDateTime getOrderedAt() { return orderedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
}
