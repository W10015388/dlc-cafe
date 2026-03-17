package com.tableorder.orderhistory.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tableorder.order.application.OrderService;
import com.tableorder.orderhistory.adapter.out.persistence.OrderHistoryRepository;
import com.tableorder.table.application.TableService;
import com.tableorder.table.domain.TableSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderHistoryServiceTest {

    @Mock private OrderHistoryRepository orderHistoryRepository;
    @Mock private OrderService orderService;
    @Mock private TableService tableService;
    @Mock private ObjectMapper objectMapper;

    @InjectMocks
    private OrderHistoryService orderHistoryService;

    @Test
    void archiveOrders_noActiveSession_doesNothing() {
        when(tableService.findActiveSession(1L)).thenReturn(Optional.empty());
        orderHistoryService.archiveOrders(1L);
        verify(orderHistoryRepository, never()).save(any());
    }

    @Test
    void archiveOrders_withSession_archivesAndCompletes() {
        TableSession session = new TableSession(1L, "token-123");
        when(tableService.findActiveSession(1L)).thenReturn(Optional.of(session));
        when(orderService.findBySessionId(any())).thenReturn(Collections.emptyList());

        orderHistoryService.archiveOrders(1L);
        verify(tableService).completeSession(1L);
    }
}
