package com.tableorder.orderhistory.adapter.in.web;

import com.tableorder.orderhistory.application.OrderHistoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order-histories")
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    public OrderHistoryController(OrderHistoryService orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @GetMapping
    public List<OrderHistoryResponse> findByTableId(@RequestParam Long tableId) {
        return orderHistoryService.findByTableId(tableId).stream()
                .map(OrderHistoryResponse::from).toList();
    }

    @PostMapping("/archive/{tableId}")
    public void archive(@PathVariable Long tableId) {
        orderHistoryService.archiveOrders(tableId);
    }
}
