# Logical Components - Backend

## 컴포넌트 구조

```
┌─────────────────────────────────────────────────┐
│                  Web Layer                        │
│  Controller (REST API + SSE Endpoint)            │
│  ├── Request DTO (@Valid)                        │
│  ├── Response DTO                                │
│  └── GlobalExceptionHandler                      │
├─────────────────────────────────────────────────┤
│                Service Layer                      │
│  ├── StoreService                                │
│  ├── StoreAdminService                           │
│  ├── CategoryService                             │
│  ├── MenuService (+ OptionGroup/Option)          │
│  ├── TableService                                │
│  ├── OrderService (+ 가격 계산, 세션 관리)        │
│  ├── OrderHistoryService                         │
│  ├── SseEventService (인메모리 Sink)             │
│  └── FileStorageService (로컬 파일)              │
├─────────────────────────────────────────────────┤
│              Persistence Layer                    │
│  Spring Data JPA Repository (per entity)         │
├─────────────────────────────────────────────────┤
│                 Database                          │
│  H2 (File Mode: ./data/tableorder)               │
├─────────────────────────────────────────────────┤
│               File System                         │
│  ./uploads/images/ (메뉴 이미지)                  │
└─────────────────────────────────────────────────┘
```

## 패키지 매핑

```
com.tableorder/
├── common/
│   ├── dto/ErrorResponse.java
│   ├── exception/NotFoundException.java
│   ├── exception/BusinessException.java
│   └── config/GlobalExceptionHandler.java
├── store/
│   ├── domain/Store.java
│   ├── application/StoreService.java
│   ├── adapter/in/web/StoreController.java
│   └── adapter/out/persistence/StoreRepository.java
├── storeadmin/
│   ├── domain/StoreAdmin.java
│   ├── application/StoreAdminService.java
│   ├── adapter/in/web/StoreAdminController.java
│   └── adapter/out/persistence/StoreAdminRepository.java
├── category/
│   ├── domain/Category.java
│   ├── application/CategoryService.java
│   ├── adapter/in/web/CategoryController.java
│   └── adapter/out/persistence/CategoryRepository.java
├── menu/
│   ├── domain/Menu.java, OptionGroup.java, Option.java
│   ├── application/MenuService.java
│   ├── adapter/in/web/MenuController.java
│   └── adapter/out/persistence/MenuRepository.java, OptionGroupRepository.java
├── table/
│   ├── domain/TableInfo.java, TableSession.java
│   ├── application/TableService.java
│   ├── adapter/in/web/TableController.java
│   └── adapter/out/persistence/TableRepository.java, TableSessionRepository.java
├── order/
│   ├── domain/Order.java, OrderItem.java, OrderItemOption.java, OrderStatus.java
│   ├── application/OrderService.java
│   ├── adapter/in/web/OrderController.java
│   └── adapter/out/persistence/OrderRepository.java
├── orderhistory/
│   ├── domain/OrderHistory.java
│   ├── application/OrderHistoryService.java
│   ├── adapter/in/web/OrderHistoryController.java
│   └── adapter/out/persistence/OrderHistoryRepository.java
├── sse/
│   ├── application/SseEventService.java
│   └── adapter/in/web/SseController.java
└── file/
    ├── application/FileStorageService.java
    └── config/WebConfig.java (정적 리소스 매핑)
```

## API 엔드포인트 요약

| Method | Path | Description |
|--------|------|-------------|
| POST | /api/v1/auth/table | 태블릿 인증 |
| GET | /api/v1/categories | 카테고리 목록 |
| POST/PUT/DELETE | /api/v1/categories | 카테고리 CRUD (슈퍼 관리자) |
| GET | /api/v1/menus?categoryId= | 메뉴 목록 |
| GET | /api/v1/menus/{id} | 메뉴 상세 (옵션 포함) |
| POST/PUT/DELETE | /api/v1/menus | 메뉴 CRUD (슈퍼 관리자) |
| POST | /api/v1/menus/{id}/image | 이미지 업로드 |
| GET | /api/v1/stores | 매장 목록 |
| POST/PUT/DELETE | /api/v1/stores | 매장 CRUD (슈퍼 관리자) |
| GET/POST/DELETE | /api/v1/store-admins | 관리자 계정 관리 |
| GET | /api/v1/tables?storeId= | 테이블 목록 |
| POST | /api/v1/orders | 주문 생성 |
| GET | /api/v1/orders?sessionId= | 세션별 주문 조회 |
| GET | /api/v1/orders?storeId= | 매장별 주문 조회 |
| PATCH | /api/v1/orders/{id}/status | 주문 상태 변경 |
| DELETE | /api/v1/orders/{id} | 주문 삭제 |
| POST | /api/v1/tables/{id}/complete | 이용 완료 |
| GET | /api/v1/order-histories?tableId= | 과거 주문 이력 |
| GET | /api/v1/events/store/{storeId} | SSE 매장 구독 |
| GET | /api/v1/events/table/{tableId} | SSE 테이블 구독 |
