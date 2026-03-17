package com.tableorder.table.adapter.in.web;

import com.tableorder.table.domain.TableInfo;
import com.tableorder.table.domain.TableSession;

public record TableResponse(Long id, Long storeId, Integer tableNumber) {
    public static TableResponse from(TableInfo t) {
        return new TableResponse(t.getId(), t.getStoreId(), t.getTableNumber());
    }
}

record TableAuthResponse(Long storeId, Long tableId, String sessionToken) {}

record TableSessionResponse(Long id, Long tableId, String sessionToken, Boolean active) {
    static TableSessionResponse from(TableSession s) {
        return new TableSessionResponse(s.getId(), s.getTableId(), s.getSessionToken(), s.getActive());
    }
}
