package com.tableorder.orderhistory.adapter.in.web;

import com.tableorder.orderhistory.domain.OrderHistory;
import java.time.LocalDateTime;

public record OrderHistoryResponse(Long id, Long tableId, Long sessionId, String orderNumber,
                                   String orderData, Integer totalAmount,
                                   LocalDateTime orderedAt, LocalDateTime completedAt) {
    public static OrderHistoryResponse from(OrderHistory h) {
        return new OrderHistoryResponse(h.getId(), h.getTableId(), h.getSessionId(),
                h.getOrderNumber(), h.getOrderData(), h.getTotalAmount(),
                h.getOrderedAt(), h.getCompletedAt());
    }
}
