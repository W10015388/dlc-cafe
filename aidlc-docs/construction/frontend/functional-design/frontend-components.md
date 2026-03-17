# Frontend Components - Functional Design

## 상태 관리 (Zustand)

### Stores

#### useCartStore
- **State**: items[], totalAmount, totalQuantity
- **Actions**: addItem(menu, options, qty), removeItem(cartItemId), updateQuantity(cartItemId, qty), clear()
- **Persistence**: localStorage (페이지 새로고침 유지)
- **Key Logic**: 동일 메뉴+동일 옵션 → 수량 증가, 다른 옵션 → 별도 항목

#### useAuthStore
- **State**: storeCode, tableNumber, sessionToken, role(customer/storeAdmin/superAdmin), selectedStoreId
- **Actions**: setTableAuth(storeCode, tableNumber, token), setRole(role), setSelectedStore(storeId), logout()
- **Persistence**: localStorage (자동 로그인)

#### useOrderStore
- **State**: orders[], loading
- **Actions**: fetchOrders(sessionId), addOrder(order), updateOrderStatus(orderId, status), removeOrder(orderId)
- **SSE Integration**: SSE 이벤트 수신 시 자동 업데이트

## 페이지별 컴포넌트 구성

### Customer Pages

#### MenuPage
- **Organisms**: CategoryNavigation, MenuGrid
- **State**: 카테고리 목록, 선택된 카테고리, 메뉴 목록
- **API**: GET /api/categories, GET /api/menus?categoryId={id}

#### MenuDetailModal
- **Organisms**: OptionGroupForm
- **State**: 선택된 옵션, 계산된 가격
- **Validation**: 필수 옵션 그룹 선택 여부 체크

#### CartPage
- **Organisms**: CartPanel (CartItem 목록)
- **State**: useCartStore
- **Actions**: 수량 조절, 삭제, 비우기, 주문하기

#### OrderConfirmPage
- **Display**: 주문 내역 요약 (메뉴명, 옵션, 수량, 금액)
- **API**: POST /api/orders
- **Success**: 주문 번호 표시 → 5초 후 MenuPage 리다이렉트 + 장바구니 클리어

#### OrderHistoryPage
- **Organisms**: OrderList
- **State**: useOrderStore
- **API**: GET /api/orders?sessionId={id}
- **SSE**: /api/events/orders?tableId={id} (상태 실시간 업데이트)

### Store Admin Pages

#### DashboardPage
- **Organisms**: TableDashboardGrid (TableCard 목록)
- **State**: 테이블별 주문 요약
- **API**: GET /api/tables?storeId={id}, GET /api/orders?storeId={id}
- **SSE**: /api/events/orders?storeId={id}
- **Actions**: 테이블 카드 클릭 → 상세, 이용 완료, 과거 내역

#### TableDetailModal
- **Display**: 테이블 전체 주문 목록 (커스텀 옵션 포함)
- **Actions**: 상태 변경 (PATCH), 주문 삭제 (DELETE)

### Super Admin Pages

#### StoreListPage / StoreFormPage
- **CRUD**: 매장 등록/조회/수정/삭제
- **API**: /api/stores

#### AdminAccountPage
- **CRUD**: 매장 관리자 계정 생성/조회/삭제
- **API**: /api/store-admins

#### MenuListPage / MenuFormPage
- **CRUD**: 메뉴 등록/조회/수정/삭제 + 옵션 그룹 관리
- **API**: /api/menus, /api/option-groups
- **File Upload**: 이미지 업로드

#### CategoryPage
- **CRUD**: 카테고리 등록/조회/수정/삭제 + 순서 조정
- **API**: /api/categories

## 라우팅 구조

```
/                       → RoleSelectPage
/customer/menu          → MenuPage (기본 화면)
/customer/cart          → CartPage
/customer/order-confirm → OrderConfirmPage
/customer/orders        → OrderHistoryPage
/customer/setup         → TableSetupPage
/admin/dashboard        → DashboardPage
/admin/history/:tableId → OrderHistoryArchivePage
/super/stores           → StoreListPage
/super/stores/new       → StoreFormPage
/super/stores/:id/edit  → StoreFormPage
/super/admins           → AdminAccountPage
/super/menus            → MenuListPage
/super/menus/new        → MenuFormPage
/super/menus/:id/edit   → MenuFormPage
/super/categories       → CategoryPage
```
