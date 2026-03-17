package com.tableorder.table.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TableAuthRequest(
        @NotBlank String storeCode,
        @NotNull Integer tableNumber,
        String password
) {}
