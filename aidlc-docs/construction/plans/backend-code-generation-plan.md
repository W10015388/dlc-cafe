# Code Generation Plan - Backend Unit

## Unit Context
- **Unit**: backend
- **Path**: `backend/`
- **Tech**: Java 17, Spring Boot 3.x, WebFlux, JPA, H2
- **Stories**: 32개 (US-C01~C10, US-A01~A09, US-S01~S09-7)

## Plan Steps

### Step 1: 프로젝트 구조 및 빌드 설정
- [x] Gradle 프로젝트 초기화 (build.gradle.kts, settings.gradle.kts)
- [x] application.yml (H2 파일 모드, 포트, CORS, 파일 업로드 설정)
- [x] 메인 애플리케이션 클래스
- [x] 패키지 구조 생성

### Step 2: 공통 모듈 (common)
- [x] ErrorResponse DTO
- [x] NotFoundException, BusinessException
- [x] GlobalExceptionHandler
- [x] WebConfig (CORS, 정적 리소스)

### Step 3: Store 도메인
- [x] Store 엔티티
- [x] StoreRepository
- [x] StoreService
- [x] StoreController (CRUD API)
- [x] Request/Response DTO
- [x] Unit Tests (StoreService)

### Step 4: StoreAdmin 도메인
- [x] StoreAdmin 엔티티
- [x] StoreAdminRepository
- [x] StoreAdminService
- [x] StoreAdminController (CRUD API)
- [x] Request/Response DTO
- [x] Unit Tests (StoreAdminService)

### Step 5: Category 도메인
- [x] Category 엔티티
- [x] CategoryRepository
- [x] CategoryService
- [x] CategoryController (CRUD API)
- [x] Request/Response DTO
- [x] Unit Tests (CategoryService)

### Step 6: Menu 도메인 (Menu + OptionGroup + Option)
- [x] Menu, OptionGroup, Option 엔티티
- [x] MenuRepository, OptionGroupRepository, OptionRepository
- [x] MenuService (메뉴 CRUD + 옵션 그룹/옵션 관리)
- [x] MenuController
- [x] Request/Response DTO
- [x] Unit Tests (MenuService)

### Step 7: FileStorage
- [x] FileStorageService (업로드, UUID 파일명, 형식/크기 검증)
- [x] Unit Tests (FileStorageService)

### Step 8: Table 도메인 (TableInfo + TableSession)
- [x] TableInfo, TableSession 엔티티
- [x] TableRepository, TableSessionRepository
- [x] TableService (테이블 CRUD, 인증, 세션 관리, 이용 완료)
- [x] TableController
- [x] Request/Response DTO
- [x] Unit Tests (TableService)

### Step 9: Order 도메인 (Order + OrderItem + OrderItemOption)
- [x] Order, OrderItem, OrderItemOption 엔티티, OrderStatus enum
- [x] OrderRepository
- [x] OrderService (주문 생성, 상태 변경, 삭제, 가격 계산, 세션 자동 생성)
- [x] OrderController
- [x] Request/Response DTO
- [x] Unit Tests (OrderService)

### Step 10: OrderHistory 도메인
- [x] OrderHistory 엔티티
- [x] OrderHistoryRepository
- [x] OrderHistoryService (아카이빙, 조회)
- [x] OrderHistoryController
- [x] Response DTO
- [x] Unit Tests (OrderHistoryService)

### Step 11: SSE 이벤트
- [x] SseEventService (인메모리 Sink, 매장별/테이블별 구독, 이벤트 발행)
- [x] SseController (SSE 엔드포인트)
- [x] Unit Tests (SseEventService)

### Step 12: Seed Data
- [x] schema.sql (필요 시)
- [x] data.sql (기본 카테고리 6개, 샘플 매장 1개)

### Step 13: Backend 코드 요약 문서
- [x] `aidlc-docs/construction/backend/code/backend-code-summary.md`
