package com.tableorder.table.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TableSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tableId;

    @Column(nullable = false, unique = true)
    private String sessionToken;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    protected TableSession() {}

    public TableSession(Long tableId, String sessionToken) {
        this.tableId = tableId;
        this.sessionToken = sessionToken;
        this.active = true;
        this.startedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getTableId() { return tableId; }
    public String getSessionToken() { return sessionToken; }
    public Boolean getActive() { return active; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }

    public void complete() {
        this.active = false;
        this.completedAt = LocalDateTime.now();
    }
}
