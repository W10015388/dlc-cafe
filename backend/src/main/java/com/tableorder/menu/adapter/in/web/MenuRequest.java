package com.tableorder.menu.adapter.in.web;

import jakarta.validation.constraints.*;

public record MenuRequest(
        @NotNull(message = "카테고리 ID는 필수입니다") Long categoryId,
        @NotBlank(message = "메뉴명은 필수입니다") @Size(max = 100) String name,
        @NotNull(message = "가격은 필수입니다") @Min(value = 0, message = "가격은 0원 이상이어야 합니다") Integer price,
        @Size(max = 500) String description,
        Integer displayOrder
) {}
