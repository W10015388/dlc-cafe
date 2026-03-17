package com.tableorder.order.adapter.in.web;

import com.tableorder.order.application.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody OrderCreateRequest request) {
        return OrderResponse.from(orderService.createOrder(request));
    }

    @GetMapping
    public List<OrderResponse> findBySessionId(@RequestParam Long sessionId) {
        return orderService.findBySessionId(sessionId).stream().map(OrderResponse::from).toList();
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return OrderResponse.from(orderService.findById(id));
    }

    @PatchMapping("/{id}/status")
    public OrderResponse advanceStatus(@PathVariable Long id) {
        return OrderResponse.from(orderService.advanceStatus(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
