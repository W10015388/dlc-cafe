package com.tableorder.order.adapter.in.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record OrderCreateRequest(
        @NotNull(message = "테이블 ID는 필수입니다") Long tableId,
        @NotEmpty(message = "주문 항목은 1개 이상이어야 합니다") List<@Valid OrderItemRequest> items
) {
    public record OrderItemRequest(
            @NotNull Long menuId,
            @NotNull @Min(1) Integer quantity,
            List<SelectedOption> selectedOptions
    ) {}

    public record SelectedOption(
            @NotNull Long optionId
    ) {}
}
