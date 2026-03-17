# Component Dependencies

## Backend Dependency Matrix

```
                Store  StoreAdmin  Category  Menu  Table  Order  OrderHistory  FileStorage  SSEEvent
Store            -                                                                          
StoreAdmin       X       -                                                                  
Category                             -                                                      
Menu                                  X        -                                X            
Table            X                                   -                                      
Order                                          X     X      -                           X   
OrderHistory                                                X       -                       
FileStorage                                                                -                
SSEEvent                                                                          -         
```

X = 의존함 (해당 행이 해당 열에 의존)

## Data Flow

### 고객 주문 플로우
```
Customer UI --> REST API Adapter
                    |
                    v
              OrderService
              /     |     \
             v      v      v
        MenuRepo TableRepo OrderRepo
                    |
                    v
            OrderEventPublisher
                    |
                    v
              SSEEventService
              /           \
             v             v
    Customer SSE     Admin SSE
    (주문 상태)    (신규 주문)
```

### 관리자 주문 상태 변경 플로우
```
Admin UI --> REST API Adapter
                  |
                  v
            OrderService
                  |
            OrderRepo.save()
                  |
                  v
          OrderEventPublisher
                  |
                  v
            SSEEventService
                  |
                  v
          Customer SSE (상태 업데이트)
```

### 이용 완료 플로우
```
Admin UI --> REST API Adapter
                  |
                  v
            TableService
                  |
                  v
          OrderHistoryService
          /               \
         v                 v
  OrderRepo.find()   OrderHistoryRepo.save()
         |
         v
  OrderRepo.delete()
         |
         v
  TableSession reset
```

## Frontend → Backend API 통신

| Frontend Page | Backend Endpoint | Method |
|---------------|-----------------|--------|
| RoleSelectPage | GET /api/stores | 매장 목록 |
| TableSetupPage | POST /api/tables/auth | 태블릿 인증 |
| MenuPage | GET /api/menus?storeId={id} | 메뉴 조회 |
| MenuPage | GET /api/menus/{id} | 메뉴 상세 |
| CartPage | (로컬 저장소) | - |
| OrderConfirmPage | POST /api/orders | 주문 생성 |
| OrderHistoryPage | GET /api/orders?sessionId={id} | 주문 내역 |
| OrderHistoryPage | SSE /api/events/orders?tableId={id} | 실시간 상태 |
| DashboardPage | GET /api/orders?storeId={id} | 매장 주문 |
| DashboardPage | SSE /api/events/orders?storeId={id} | 실시간 주문 |
| DashboardPage | PATCH /api/orders/{id}/status | 상태 변경 |
| DashboardPage | DELETE /api/orders/{id} | 주문 삭제 |
| DashboardPage | POST /api/tables/{id}/complete | 이용 완료 |
| OrderHistoryArchivePage | GET /api/order-history?tableId={id} | 과거 내역 |
| StoreFormPage | POST/PUT /api/stores | 매장 CRUD |
| AdminAccountPage | POST/DELETE /api/store-admins | 계정 관리 |
| MenuFormPage | POST/PUT/DELETE /api/menus | 메뉴 CRUD |
| MenuFormPage | POST/PUT/DELETE /api/option-groups | 옵션 관리 |
| CategoryPage | POST/PUT/DELETE /api/categories | 카테고리 CRUD |
