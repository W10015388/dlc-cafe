package com.tableorder.orderhistory.adapter.out.persistence;

import com.tableorder.orderhistory.domain.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findByTableIdOrderByCompletedAtDesc(Long tableId);
}
