package com.tableorder.order.application;

import com.tableorder.common.exception.BusinessException;
import com.tableorder.order.domain.Order;
import com.tableorder.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrderServiceTest {

    @Test
    void orderStatus_forwardOnly() {
        assertThat(OrderStatus.PENDING.next()).isEqualTo(OrderStatus.PREPARING);
        assertThat(OrderStatus.PREPARING.next()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    void orderStatus_completedCannotAdvance() {
        assertThatThrownBy(() -> OrderStatus.COMPLETED.next())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void order_advanceStatus() {
        Order order = new Order(1L, "ORD-20260317-001", 10000);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        order.advanceStatus();
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PREPARING);
        order.advanceStatus();
        assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    void order_totalAmount() {
        Order order = new Order(1L, "ORD-20260317-001", 15000);
        assertThat(order.getTotalAmount()).isEqualTo(15000);
    }
}
