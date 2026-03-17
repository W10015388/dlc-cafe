package com.tableorder.storeadmin.adapter.out.persistence;

import com.tableorder.storeadmin.domain.StoreAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StoreAdminRepository extends JpaRepository<StoreAdmin, Long> {
    List<StoreAdmin> findByStoreId(Long storeId);
    boolean existsByUsername(String username);
}
