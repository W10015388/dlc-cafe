package com.tableorder.storeadmin.adapter.in.web;

import com.tableorder.storeadmin.domain.StoreAdmin;
import java.time.LocalDateTime;

public record StoreAdminResponse(Long id, String username, Long storeId, LocalDateTime createdAt) {
    public static StoreAdminResponse from(StoreAdmin admin) {
        return new StoreAdminResponse(admin.getId(), admin.getUsername(), admin.getStoreId(), admin.getCreatedAt());
    }
}
