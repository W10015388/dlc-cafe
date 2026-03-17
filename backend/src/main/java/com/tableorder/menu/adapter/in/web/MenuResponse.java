package com.tableorder.menu.adapter.in.web;

import com.tableorder.menu.domain.Menu;
import com.tableorder.menu.domain.OptionGroup;
import com.tableorder.menu.domain.Option;
import java.util.List;

public record MenuResponse(Long id, Long categoryId, String name, Integer price, String description,
                           String imageUrl, Integer displayOrder, List<OptionGroupResponse> optionGroups) {

    public static MenuResponse from(Menu m) {
        List<OptionGroupResponse> groups = m.getOptionGroups().stream()
                .map(OptionGroupResponse::from).toList();
        return new MenuResponse(m.getId(), m.getCategoryId(), m.getName(), m.getPrice(),
                m.getDescription(), m.getImageUrl(), m.getDisplayOrder(), groups);
    }

    public record OptionGroupResponse(Long id, String name, String selectionType, Boolean required,
                                      Integer displayOrder, List<OptionResponse> options) {
        public static OptionGroupResponse from(OptionGroup g) {
            List<OptionResponse> opts = g.getOptions().stream().map(OptionResponse::from).toList();
            return new OptionGroupResponse(g.getId(), g.getName(), g.getSelectionType().name(),
                    g.getRequired(), g.getDisplayOrder(), opts);
        }
    }

    public record OptionResponse(Long id, String name, Integer additionalPrice, Integer displayOrder) {
        public static OptionResponse from(Option o) {
            return new OptionResponse(o.getId(), o.getName(), o.getAdditionalPrice(), o.getDisplayOrder());
        }
    }
}
