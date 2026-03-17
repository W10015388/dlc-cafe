# Services

## Backend Service Layer

헥사고날 아키텍처에서 Service는 Inbound Port(UseCase)의 구현체로, 도메인 로직을 오케스트레이션합니다.

### StoreService
- **Implements**: StoreUseCase
- **Dependencies**: StoreRepository
- **Orchestration**: 매장 CRUD, 매장 식별자 중복 검증

### StoreAdminService
- **Implements**: StoreAdminUseCase
- **Dependencies**: StoreAdminRepository, StoreRepository
- **Orchestration**: 관리자 계정 CRUD, 매장 존재 검증, 사용자명 중복 검증

### CategoryService
- **Implements**: CategoryUseCase
- **Dependencies**: CategoryRepository
- **Orchestration**: 카테고리 CRUD, 순서 관리, 중복명 검증

### MenuService
- **Implements**: MenuUseCase
- **Dependencies**: MenuRepository, CategoryRepository, FileStoragePort
- **Orchestration**: 메뉴 CRUD, 옵션 그룹 관리, 이미지 업로드 연동, 카테고리 존재 검증, 가격 검증

### TableService
- **Implements**: TableUseCase
- **Dependencies**: TableRepository, StoreRepository
- **Orchestration**: 테이블 생성, 태블릿 인증, 세션 관리, 이용 완료 처리 → OrderHistoryService 연동

### OrderService
- **Implements**: OrderUseCase
- **Dependencies**: OrderRepository, TableRepository, MenuRepository, OrderEventPublisher
- **Orchestration**:
  - 주문 생성: 메뉴/옵션 유효성 검증 → 가격 계산 → 주문 저장 → 첫 주문 시 세션 생성 → 이벤트 발행
  - 상태 변경: 상태 전이 검증 → 업데이트 → 이벤트 발행
  - 주문 삭제: 삭제 → 이벤트 발행

### OrderHistoryService
- **Implements**: OrderHistoryUseCase
- **Dependencies**: OrderHistoryRepository, OrderRepository
- **Orchestration**: 이용 완료 시 현재 주문 → 이력 아카이빙, 날짜 기반 이력 조회

### FileStorageService
- **Implements**: FileStoragePort
- **Orchestration**: 로컬 파일 시스템 저장/조회/삭제

### SSEEventService
- **Implements**: OrderEventPublisher
- **Dependencies**: Reactor Sinks (WebFlux)
- **Orchestration**: SSE 구독자 관리, 매장별/테이블별 이벤트 브로드캐스팅

## Frontend Service Layer

### API Service
- **Purpose**: Backend REST API 호출 추상화
- **Modules**: storeApi, categoryApi, menuApi, tableApi, orderApi, adminApi, fileApi

### SSE Service
- **Purpose**: SSE 연결 관리 및 이벤트 수신
- **Modules**: orderSSE (주문 이벤트 구독/해제)

### Cart Service (Local)
- **Purpose**: 장바구니 로컬 저장소 관리
- **Storage**: localStorage
- **Operations**: add, remove, updateQuantity, clear, getAll, getTotal
