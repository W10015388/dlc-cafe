package com.tableorder.storeadmin.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class StoreAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected StoreAdmin() {}

    public StoreAdmin(String username, Long storeId) {
        this.username = username;
        this.storeId = storeId;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public Long getStoreId() { return storeId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
