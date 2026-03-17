package com.tableorder.table.adapter.out.persistence;

import com.tableorder.table.domain.TableSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TableSessionRepository extends JpaRepository<TableSession, Long> {
    Optional<TableSession> findByTableIdAndActiveTrue(Long tableId);
}
