package com.tableorder.category.adapter.in.web;

import com.tableorder.category.domain.Category;

public record CategoryResponse(Long id, String name, Integer displayOrder) {
    public static CategoryResponse from(Category c) {
        return new CategoryResponse(c.getId(), c.getName(), c.getDisplayOrder());
    }
}
