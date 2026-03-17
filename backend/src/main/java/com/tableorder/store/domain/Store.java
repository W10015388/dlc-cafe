package com.tableorder.store.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String storeCode;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Store() {}

    public Store(String name, String storeCode) {
        this.name = name;
        this.storeCode = storeCode;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getStoreCode() { return storeCode; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void update(String name, String storeCode) {
        this.name = name;
        this.storeCode = storeCode;
    }
}
