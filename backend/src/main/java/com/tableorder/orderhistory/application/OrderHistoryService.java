package com.tableorder.orderhistory.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tableorder.order.adapter.in.web.OrderResponse;
import com.tableorder.order.application.OrderService;
import com.tableorder.order.domain.Order;
import com.tableorder.orderhistory.adapter.out.persistence.OrderHistoryRepository;
import com.tableorder.orderhistory.domain.OrderHistory;
import com.tableorder.table.application.TableService;
import com.tableorder.table.domain.TableSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderService orderService;
    private final TableService tableService;
    private final ObjectMapper objectMapper;

    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository, OrderService orderService,
                               TableService tableService, ObjectMapper objectMapper) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.orderService = orderService;
        this.tableService = tableService;
        this.objectMapper = objectMapper;
    }

    /**
     * 이용 완료 시 주문을 아카이빙
     */
    public void archiveOrders(Long tableId) {
        TableSession session = tableService.findActiveSession(tableId)
                .orElse(null);
        if (session == null) return;

        List<Order> orders = orderService.findBySessionId(session.getId());
        LocalDateTime completedAt = LocalDateTime.now();

        for (Order order : orders) {
            String orderData;
            try {
                orderData = objectMapper.writeValueAsString(OrderResponse.from(order));
            } catch (JsonProcessingException e) {
                orderData = "{}";
            }
            orderHistoryRepository.save(new OrderHistory(
                    tableId, session.getId(), order.getOrderNumber(),
                    orderData, order.getTotalAmount(), order.getCreatedAt(), completedAt
            ));
        }

        tableService.completeSession(tableId);
    }

    @Transactional(readOnly = true)
    public List<OrderHistory> findByTableId(Long tableId) {
        return orderHistoryRepository.findByTableIdOrderByCompletedAtDesc(tableId);
    }
}
