package com.tableorder.menu.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long menuId;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SelectionType selectionType;

    @Column(nullable = false)
    private Boolean required;

    @Column(nullable = false)
    private Integer displayOrder = 0;

    @OneToMany(mappedBy = "optionGroupId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Option> options = new ArrayList<>();

    protected OptionGroup() {}

    public OptionGroup(Long menuId, String name, SelectionType selectionType, Boolean required, Integer displayOrder) {
        this.menuId = menuId;
        this.name = name;
        this.selectionType = selectionType;
        this.required = required;
        this.displayOrder = displayOrder;
    }

    public Long getId() { return id; }
    public Long getMenuId() { return menuId; }
    public String getName() { return name; }
    public SelectionType getSelectionType() { return selectionType; }
    public Boolean getRequired() { return required; }
    public Integer getDisplayOrder() { return displayOrder; }
    public List<Option> getOptions() { return options; }

    public void update(String name, SelectionType selectionType, Boolean required, Integer displayOrder) {
        this.name = name;
        this.selectionType = selectionType;
        this.required = required;
        this.displayOrder = displayOrder;
    }

    public enum SelectionType { SINGLE, MULTIPLE }
}
