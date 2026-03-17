package com.tableorder.store.adapter.out.persistence;

import com.tableorder.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByStoreCode(String storeCode);
    boolean existsByStoreCode(String storeCode);
}
