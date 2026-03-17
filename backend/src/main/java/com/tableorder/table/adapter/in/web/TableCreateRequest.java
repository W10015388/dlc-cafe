package com.tableorder.table.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record TableCreateRequest(
        @NotNull Long storeId,
        @NotNull Integer tableNumber,
        @NotBlank String password
) {}
