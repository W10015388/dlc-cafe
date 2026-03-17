package com.tableorder.order.domain;

public enum OrderStatus {
    PENDING, PREPARING, COMPLETED;

    public OrderStatus next() {
        return switch (this) {
            case PENDING -> PREPARING;
            case PREPARING -> COMPLETED;
            case COMPLETED -> throw new IllegalStateException("완료된 주문은 상태를 변경할 수 없습니다");
        };
    }
}
