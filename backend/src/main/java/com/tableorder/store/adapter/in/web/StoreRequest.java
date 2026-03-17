package com.tableorder.store.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StoreRequest(
        @NotBlank(message = "매장명은 필수입니다")
        @Size(max = 100, message = "매장명은 100자 이내여야 합니다")
        String name,

        @NotBlank(message = "매장 코드는 필수입니다")
        @Size(max = 50, message = "매장 코드는 50자 이내여야 합니다")
        String storeCode
) {}
