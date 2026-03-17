package com.tableorder.order.adapter.out.persistence;

import com.tableorder.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findBySessionIdOrderByCreatedAtDesc(Long sessionId);
    List<Order> findBySessionIdIn(List<Long> sessionIds);
    long countByOrderNumberStartingWith(String prefix);
}
