package com.tableorder.order.application;

import com.tableorder.common.exception.BusinessException;
import com.tableorder.common.exception.NotFoundException;
import com.tableorder.menu.adapter.out.persistence.MenuRepository;
import com.tableorder.menu.adapter.out.persistence.OptionRepository;
import com.tableorder.menu.domain.Menu;
import com.tableorder.menu.domain.Option;
import com.tableorder.menu.domain.OptionGroup;
import com.tableorder.order.adapter.in.web.OrderCreateRequest;
import com.tableorder.order.adapter.out.persistence.OrderItemRepository;
import com.tableorder.order.adapter.out.persistence.OrderRepository;
import com.tableorder.order.domain.*;
import com.tableorder.sse.application.SseEventService;
import com.tableorder.table.application.TableService;
import com.tableorder.table.domain.TableSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuRepository menuRepository;
    private final OptionRepository optionRepository;
    private final TableService tableService;
    private final SseEventService sseEventService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        MenuRepository menuRepository, OptionRepository optionRepository,
                        TableService tableService, SseEventService sseEventService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.menuRepository = menuRepository;
        this.optionRepository = optionRepository;
        this.tableService = tableService;
        this.sseEventService = sseEventService;
    }

    public Order createOrder(OrderCreateRequest request) {
        TableSession session = tableService.getOrCreateSession(request.tableId());
        String orderNumber = generateOrderNumber();

        Order order = new Order(session.getId(), orderNumber, 0);
        order = orderRepository.save(order);

        int totalAmount = 0;
        for (OrderCreateRequest.OrderItemRequest itemReq : request.items()) {
            Menu menu = menuRepository.findById(itemReq.menuId())
                    .orElseThrow(() -> new NotFoundException("메뉴를 찾을 수 없습니다: " + itemReq.menuId()));

            int optionPrice = 0;
            if (itemReq.selectedOptions() != null && !itemReq.selectedOptions().isEmpty()) {
                for (OrderCreateRequest.SelectedOption selOpt : itemReq.selectedOptions()) {
                    Option option = optionRepository.findById(selOpt.optionId())
                            .orElseThrow(() -> new NotFoundException("옵션을 찾을 수 없습니다: " + selOpt.optionId()));
                    optionPrice += option.getAdditionalPrice();
                }
            }

            int unitPrice = menu.getPrice() + optionPrice;
            OrderItem item = new OrderItem(order.getId(), menu.getId(), menu.getName(), itemReq.quantity(), unitPrice);
            item = orderItemRepository.save(item);

            if (itemReq.selectedOptions() != null && !itemReq.selectedOptions().isEmpty()) {
                for (OrderCreateRequest.SelectedOption selOpt : itemReq.selectedOptions()) {
                    Option option = optionRepository.findById(selOpt.optionId()).orElseThrow();
                    OptionGroup group = menu.getOptionGroups().stream()
                            .filter(g -> g.getOptions().stream().anyMatch(o -> o.getId().equals(option.getId())))
                            .findFirst().orElse(null);
                    item.getOptions().add(new OrderItemOption(item.getId(), group != null ? group.getName() : "", option.getName(), option.getAdditionalPrice()));
                }
                orderItemRepository.save(item);
            }

            totalAmount += unitPrice * itemReq.quantity();
        }

        order.updateTotalAmount(totalAmount);
        order = orderRepository.save(order);

        sseEventService.publishToTable(request.tableId(), "NEW_ORDER", Map.of("orderId", order.getId(), "orderNumber", orderNumber));

        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> findBySessionId(Long sessionId) {
        return orderRepository.findBySessionIdOrderByCreatedAtDesc(sessionId);
    }

    @Transactional(readOnly = true)
    public List<Order> findBySessionIds(List<Long> sessionIds) {
        return orderRepository.findBySessionIdIn(sessionIds);
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다: " + id));
    }

    public Order advanceStatus(Long orderId) {
        Order order = findById(orderId);
        try {
            order.advanceStatus();
        } catch (IllegalStateException e) {
            throw new BusinessException(e.getMessage());
        }
        order = orderRepository.save(order);
        sseEventService.publishToTable(null, "STATUS_CHANGED", Map.of("orderId", orderId, "status", order.getStatus().name()));
        return order;
    }

    public void deleteOrder(Long orderId) {
        Order order = findById(orderId);
        orderRepository.delete(order);
        sseEventService.publishToTable(null, "ORDER_DELETED", Map.of("orderId", orderId));
    }

    private String generateOrderNumber() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "ORD-" + date + "-" + random;
    }
}
