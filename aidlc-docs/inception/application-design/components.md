# Components

## Backend Components (Hexagonal Architecture)

### Domain Layer (Core)

#### 1. Store Domain
- **Purpose**: 매장 정보 관리
- **Responsibilities**: 매장 엔티티, 매장 비즈니스 규칙
- **Ports**: StoreRepository (outbound), StoreUseCase (inbound)

#### 2. StoreAdmin Domain
- **Purpose**: 매장 관리자 계정 관리
- **Responsibilities**: 관리자 계정 엔티티, 계정 비즈니스 규칙
- **Ports**: StoreAdminRepository (outbound), StoreAdminUseCase (inbound)

#### 3. Category Domain
- **Purpose**: 메뉴 카테고리 관리
- **Responsibilities**: 카테고리 엔티티, 순서 관리
- **Ports**: CategoryRepository (outbound), CategoryUseCase (inbound)

#### 4. Menu Domain
- **Purpose**: 메뉴 및 커스텀 옵션 관리
- **Responsibilities**: 메뉴 엔티티, 옵션 그룹/옵션 엔티티, 가격 계산
- **Ports**: MenuRepository (outbound), MenuUseCase (inbound)

#### 5. Table Domain
- **Purpose**: 테이블 및 세션 관리
- **Responsibilities**: 테이블 엔티티, 세션 라이프사이클, 태블릿 인증
- **Ports**: TableRepository (outbound), TableUseCase (inbound)

#### 6. Order Domain
- **Purpose**: 주문 처리 및 상태 관리
- **Responsibilities**: 주문 엔티티, 주문 항목(커스텀 옵션 포함), 상태 전이, 세션 기반 주문 그룹화
- **Ports**: OrderRepository (outbound), OrderUseCase (inbound), OrderEventPublisher (outbound)

#### 7. OrderHistory Domain
- **Purpose**: 과거 주문 이력 관리
- **Responsibilities**: 이용 완료된 주문 아카이빙, 이력 조회
- **Ports**: OrderHistoryRepository (outbound), OrderHistoryUseCase (inbound)

### Adapter Layer (Inbound)

#### 8. REST API Adapter (Web)
- **Purpose**: HTTP REST API 엔드포인트 제공
- **Responsibilities**: 요청 검증, DTO 변환, 응답 포맷팅
- **Controllers**: StoreController, CategoryController, MenuController, TableController, OrderController, AdminController

#### 9. SSE Adapter (WebFlux)
- **Purpose**: 실시간 이벤트 스트리밍
- **Responsibilities**: SSE 연결 관리, 이벤트 브로드캐스팅
- **Controllers**: OrderEventController (주문 상태 변경, 신규 주문 알림)

#### 10. File Upload Adapter
- **Purpose**: 이미지 파일 업로드 처리
- **Responsibilities**: 파일 수신, 검증, 저장 위임

### Adapter Layer (Outbound)

#### 11. JPA Persistence Adapter
- **Purpose**: H2 데이터베이스 영속성
- **Responsibilities**: JPA Entity 매핑, Repository 구현
- **Implements**: 각 도메인의 Repository Port

#### 12. File Storage Adapter
- **Purpose**: 로컬 파일 시스템 이미지 저장
- **Responsibilities**: 파일 저장/조회/삭제
- **Implements**: FileStoragePort

#### 13. Event Publisher Adapter
- **Purpose**: 도메인 이벤트를 SSE로 전달
- **Responsibilities**: 이벤트 발행, 구독자 관리
- **Implements**: OrderEventPublisher Port

---

## Frontend Components (Atomic Design)

### Atoms
- Button, Input, Icon, Badge, Image, Text, Spinner, Card

### Molecules
- MenuCard, CartItem, OrderStatusBadge, CategoryTab, OptionSelector, ConfirmDialog, TableCard, FormField

### Organisms
- MenuGrid, CartPanel, OrderList, CategoryNavigation, OptionGroupForm, TableDashboardGrid, MenuManagementTable, StoreManagementTable

### Templates
- CustomerLayout (메뉴 + 장바구니 + 주문내역 네비게이션)
- StoreAdminLayout (대시보드 + 테이블관리 네비게이션)
- SuperAdminLayout (매장관리 + 계정관리 + 메뉴관리 + 카테고리관리 네비게이션)

### Pages
- Customer: MenuPage, CartPage, OrderConfirmPage, OrderHistoryPage, TableSetupPage
- StoreAdmin: DashboardPage, TableDetailPage, OrderHistoryArchivePage
- SuperAdmin: StoreListPage, StoreFormPage, AdminAccountPage, MenuListPage, MenuFormPage, CategoryPage
- Common: RoleSelectPage
