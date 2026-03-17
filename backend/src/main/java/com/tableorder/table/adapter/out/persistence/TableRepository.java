package com.tableorder.table.adapter.out.persistence;

import com.tableorder.table.domain.TableInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<TableInfo, Long> {
    List<TableInfo> findByStoreId(Long storeId);
    Optional<TableInfo> findByStoreIdAndTableNumber(Long storeId, Integer tableNumber);
}
