package com.tableorder.store.adapter.in.web;

import com.tableorder.store.domain.Store;
import java.time.LocalDateTime;

public record StoreResponse(Long id, String name, String storeCode, LocalDateTime createdAt) {
    public static StoreResponse from(Store store) {
        return new StoreResponse(store.getId(), store.getName(), store.getStoreCode(), store.getCreatedAt());
    }
}
