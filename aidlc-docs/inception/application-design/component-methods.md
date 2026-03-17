# Component Methods

## Domain Use Cases (Inbound Ports)

### StoreUseCase
| Method | Input | Output | Purpose |
|--------|-------|--------|---------|
| createStore | CreateStoreCommand | Store | 매장 등록 |
| getStore | Long storeId | Store | 매장 조회 |
| getAllStores | - | List<Store> | 전체 매장 목록 |
| updateStore | UpdateStoreCommand | Store | 매장 수정 |
| deleteStore | Long storeId | void | 매장 삭제 |

### StoreAdminUseCase
| Method | Input | Output | Purpose |
|--------|-------|--------|---------|
| createAdmin | CreateAdminCommand | StoreAdmin | 매장 관리자 계정 생성 |
| getAllAdmins | - | List<StoreAdmin> | 전체 관리자 목록 |
| deleteAdmin | Long adminId | void | 관리자 계정 삭제 |

### CategoryUseCase
| Method | Input | Output | Purpose |
|--------|-------|--------|---------|
| createCategory | CreateCategoryCommand | Category | 카테고리 등록 |
| getAllCategories | - | List<Category> | 전체 카테고리 목록 (순서대로) |
| updateCategory | UpdateCategoryCommand | Category | 카테고리 수정 |
| deleteCategory | Long categoryId | void | 카테고리 삭제 |
| reorderCategories | List<ReorderCommand> | void | 카테고리 순서 변경 |

### MenuUseCase
| Method | Input | Output | Purpose |
|--------|-------|--------|---------|
| createMenu | CreateMenuCommand | Menu | 메뉴 등록 |
| getMenusByCategory | Long categoryId | List<Menu> | 카테고리별 메뉴 조회 |
| getMenuDetail | Long menuId | Menu (with options) | 메뉴 상세 (옵션 포함) |
| updateMenu | UpdateMenuCommand | Menu | 메뉴 수정 |
| deleteMenu | Long menuId | void | 메뉴 삭제 |
| reorderMenus | List<ReorderCommand> | void | 메뉴 순서 변경 |
| addOptionGroup | AddOptionGroupCommand | OptionGroup | 옵션 그룹 추가 |
| updateOptionGroup | UpdateOptionGroupCommand | OptionGroup | 옵션 그룹 수정 |
| deleteOptionGroup | Long optionGroupId | void | 옵션 그룹 삭제 |

### TableUseCase
| Method | Input | Output | Purpose |
|--------|-------|--------|---------|
| createTable | CreateTableCommand | TableInfo | 테이블 생성 (비밀번호 설정) |
| authenticateTable | AuthTableCommand | TableSession | 태블릿 인증 (매장ID+테이블번호+비밀번호) |
| getTablesByStore | Long storeId | List<TableInfo> | 매장별 테이블 목록 |
| completeTableSession | Long tableId | void | 이용 완료 (세션 종료) |

### OrderUseCase
| Method | Input | Output | Purpose |
|--------|-------|--------|---------|
| createOrder | CreateOrderCommand | Order | 주문 생성 (첫 주문 시 세션 자동 생성) |
| getOrdersBySession | String sessionId | List<Order> | 세션별 주문 목록 |
| getOrdersByTable | Long tableId | List<Order> | 테이블 현재 주문 목록 |
| updateOrderStatus | UpdateStatusCommand | Order | 주문 상태 변경 (대기중→준비중→완료) |
| deleteOrder | Long orderId | void | 주문 삭제 |

### OrderHistoryUseCase
| Method | Input | Output | Purpose |
|--------|-------|--------|---------|
| archiveOrders | Long tableId | void | 이용 완료 시 주문 아카이빙 |
| getHistory | HistoryQuery | List<OrderHistory> | 과거 주문 내역 조회 (날짜 필터) |

## Outbound Ports (Repository Interfaces)

각 도메인별 Repository Port는 표준 CRUD + 도메인 특화 쿼리 메서드를 정의.
상세 구현은 JPA Persistence Adapter에서 처리.

### FileStoragePort
| Method | Input | Output | Purpose |
|--------|-------|--------|---------|
| store | MultipartFile | String (filePath) | 파일 저장, 경로 반환 |
| load | String filePath | Resource | 파일 로드 |
| delete | String filePath | void | 파일 삭제 |

### OrderEventPublisher
| Method | Input | Output | Purpose |
|--------|-------|--------|---------|
| publishNewOrder | OrderEvent | void | 신규 주문 이벤트 발행 |
| publishStatusChange | OrderStatusEvent | void | 주문 상태 변경 이벤트 발행 |
| publishOrderDeleted | OrderDeletedEvent | void | 주문 삭제 이벤트 발행 |
