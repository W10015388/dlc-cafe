package com.tableorder.table.domain;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"storeId", "tableNumber"}))
public class TableInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private Integer tableNumber;

    @Column(nullable = false)
    private String password;

    protected TableInfo() {}

    public TableInfo(Long storeId, Integer tableNumber, String password) {
        this.storeId = storeId;
        this.tableNumber = tableNumber;
        this.password = password;
    }

    public Long getId() { return id; }
    public Long getStoreId() { return storeId; }
    public Integer getTableNumber() { return tableNumber; }
    public String getPassword() { return password; }
}
