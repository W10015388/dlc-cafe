package com.tableorder.storeadmin.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoreAdminRequest(
        @NotBlank(message = "사용자명은 필수입니다") String username,
        @NotNull(message = "매장 ID는 필수입니다") Long storeId
) {}
