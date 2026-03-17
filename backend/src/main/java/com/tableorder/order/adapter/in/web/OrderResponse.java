package com.tableorder.order.adapter.in.web;

import com.tableorder.order.domain.Order;
import com.tableorder.order.domain.OrderItem;
import com.tableorder.order.domain.OrderItemOption;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long id, Long sessionId, String orderNumber, String status,
                            Integer totalAmount, LocalDateTime createdAt, List<OrderItemResponse> items) {

    public static OrderResponse from(Order o) {
        List<OrderItemResponse> items = o.getItems().stream().map(OrderItemResponse::from).toList();
        return new OrderResponse(o.getId(), o.getSessionId(), o.getOrderNumber(),
                o.getStatus().name(), o.getTotalAmount(), o.getCreatedAt(), items);
    }

    public record OrderItemResponse(Long id, Long menuId, String menuName, Integer quantity,
                                    Integer unitPrice, List<OrderItemOptionResponse> options) {
        public static OrderItemResponse from(OrderItem i) {
            List<OrderItemOptionResponse> opts = i.getOptions().stream().map(OrderItemOptionResponse::from).toList();
            return new OrderItemResponse(i.getId(), i.getMenuId(), i.getMenuName(),
                    i.getQuantity(), i.getUnitPrice(), opts);
        }
    }

    public record OrderItemOptionResponse(String optionGroupName, String optionName, Integer additionalPrice) {
        public static OrderItemOptionResponse from(OrderItemOption o) {
            return new OrderItemOptionResponse(o.getOptionGroupName(), o.getOptionName(), o.getAdditionalPrice());
        }
    }
}
