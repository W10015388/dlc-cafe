package com.tableorder.menu.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public record OptionGroupRequest(
        @NotBlank(message = "옵션 그룹명은 필수입니다") String name,
        @NotNull(message = "선택 유형은 필수입니다") String selectionType,
        @NotNull(message = "필수 여부는 필수입니다") Boolean required,
        Integer displayOrder
) {}

record OptionRequest(
        @NotBlank(message = "옵션명은 필수입니다") String name,
        @NotNull @Min(value = 0, message = "추가 금액은 0원 이상이어야 합니다") Integer additionalPrice,
        Integer displayOrder
) {}
