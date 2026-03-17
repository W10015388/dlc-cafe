# Unit of Work

## Unit 1: Backend

- **Name**: backend
- **Type**: Module (모노리스 내 단일 Spring Boot 애플리케이션)
- **Path**: `backend/`
- **Tech Stack**: Java, Spring Boot, Spring WebFlux, Spring Data JPA, H2
- **Responsibilities**:
  - REST API 엔드포인트 (Store, StoreAdmin, Category, Menu, Table, Order, OrderHistory)
  - SSE 이벤트 스트리밍 (WebFlux)
  - 데이터 영속성 (JPA + H2)
  - 파일 저장 (로컬 파일 시스템)
  - 비즈니스 로직 (헥사고날 아키텍처)
- **Code Organization**:
  ```
  backend/src/main/java/com/tableorder/
    store/          (domain/ application/ adapter/)
    storeadmin/     (domain/ application/ adapter/)
    category/       (domain/ application/ adapter/)
    menu/           (domain/ application/ adapter/)
    table/          (domain/ application/ adapter/)
    order/          (domain/ application/ adapter/)
    orderhistory/   (domain/ application/ adapter/)
    file/           (adapter)
    sse/            (adapter)
    common/         (config, exception, util)
  ```

## Unit 2: Frontend

- **Name**: frontend
- **Type**: Module (단일 React SPA)
- **Path**: `frontend/`
- **Tech Stack**: React, TypeScript, Atomic Design
- **Responsibilities**:
  - 고객용 UI (메뉴 조회, 커스텀 옵션, 장바구니, 주문, 주문 내역)
  - 매장 관리자 UI (대시보드, 주문 관리, 테이블 관리)
  - 슈퍼 관리자 UI (매장 관리, 계정 관리, 메뉴/카테고리 관리)
  - 역할 선택 UI
  - SSE 이벤트 수신
  - 장바구니 로컬 저장
- **Code Organization**:
  ```
  frontend/src/
    components/
      atoms/        (Button, Input, Icon, Badge, etc.)
      molecules/    (MenuCard, CartItem, OptionSelector, etc.)
      organisms/    (MenuGrid, CartPanel, OrderList, etc.)
      templates/    (CustomerLayout, StoreAdminLayout, SuperAdminLayout)
    pages/
      customer/     (MenuPage, CartPage, OrderConfirmPage, etc.)
      store-admin/  (DashboardPage, TableDetailPage, etc.)
      super-admin/  (StoreListPage, MenuListPage, CategoryPage, etc.)
      common/       (RoleSelectPage)
    services/       (API, SSE, Cart)
    hooks/
    types/
  ```
