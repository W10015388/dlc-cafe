# Domain Entities - Backend

## Entity Relationship

```
Store 1---* Table
Store 1---* StoreAdmin
Category 1---* Menu
Menu 1---* OptionGroup
OptionGroup 1---* Option
Table 1---* TableSession
TableSession 1---* Order
Order 1---* OrderItem
OrderItem 1---* OrderItemOption
TableSession 1---* OrderHistory (아카이빙 후)
```

## Entities

### Store
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 매장 ID |
| name | String | NOT NULL, max 100 | 매장명 |
| storeCode | String | NOT NULL, UNIQUE, max 50 | 매장 식별자 |
| createdAt | LocalDateTime | NOT NULL | 생성일시 |

### StoreAdmin
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 관리자 ID |
| username | String | NOT NULL, UNIQUE, max 50 | 사용자명 |
| storeId | Long | FK(Store), NOT NULL | 할당 매장 |
| createdAt | LocalDateTime | NOT NULL | 생성일시 |

### Category
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 카테고리 ID |
| name | String | NOT NULL, UNIQUE, max 50 | 카테고리명 |
| displayOrder | Integer | NOT NULL, default 0 | 노출 순서 |

### Menu
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 메뉴 ID |
| categoryId | Long | FK(Category), NOT NULL | 카테고리 |
| name | String | NOT NULL, max 100 | 메뉴명 |
| price | Integer | NOT NULL, min 0 | 기본 가격 (원) |
| description | String | max 500 | 메뉴 설명 |
| imageUrl | String | max 255 | 이미지 경로 |
| displayOrder | Integer | NOT NULL, default 0 | 노출 순서 |
| createdAt | LocalDateTime | NOT NULL | 생성일시 |

### OptionGroup
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 옵션 그룹 ID |
| menuId | Long | FK(Menu), NOT NULL | 메뉴 |
| name | String | NOT NULL, max 50 | 그룹명 (예: 사이즈, 온도) |
| selectionType | Enum | SINGLE/MULTIPLE | 선택 유형 |
| required | Boolean | NOT NULL | 필수 여부 |
| displayOrder | Integer | NOT NULL, default 0 | 노출 순서 |

### Option
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 옵션 ID |
| optionGroupId | Long | FK(OptionGroup), NOT NULL | 옵션 그룹 |
| name | String | NOT NULL, max 50 | 옵션명 (예: Tall, Grande) |
| additionalPrice | Integer | NOT NULL, default 0 | 추가 금액 (원) |
| displayOrder | Integer | NOT NULL, default 0 | 노출 순서 |

### TableInfo
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 테이블 ID |
| storeId | Long | FK(Store), NOT NULL | 매장 |
| tableNumber | Integer | NOT NULL | 테이블 번호 |
| password | String | NOT NULL | 테이블 비밀번호 |
| UNIQUE(storeId, tableNumber) | | | 매장 내 테이블 번호 유니크 |

### TableSession
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 세션 ID |
| tableId | Long | FK(TableInfo), NOT NULL | 테이블 |
| sessionToken | String | NOT NULL, UNIQUE | 세션 토큰 (UUID) |
| active | Boolean | NOT NULL, default true | 활성 여부 |
| startedAt | LocalDateTime | NOT NULL | 세션 시작 (첫 주문 시) |
| completedAt | LocalDateTime | | 이용 완료 시각 |

### Order
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 주문 ID |
| sessionId | Long | FK(TableSession), NOT NULL | 테이블 세션 |
| orderNumber | String | NOT NULL, UNIQUE | 주문 번호 |
| status | Enum | PENDING/PREPARING/COMPLETED | 주문 상태 |
| totalAmount | Integer | NOT NULL | 총 주문 금액 |
| createdAt | LocalDateTime | NOT NULL | 주문 시각 |

### OrderItem
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 주문 항목 ID |
| orderId | Long | FK(Order), NOT NULL | 주문 |
| menuId | Long | FK(Menu), NOT NULL | 메뉴 |
| menuName | String | NOT NULL | 메뉴명 (스냅샷) |
| quantity | Integer | NOT NULL, min 1 | 수량 |
| unitPrice | Integer | NOT NULL | 단가 (기본가 + 옵션 추가금) |

### OrderItemOption
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 주문 항목 옵션 ID |
| orderItemId | Long | FK(OrderItem), NOT NULL | 주문 항목 |
| optionGroupName | String | NOT NULL | 옵션 그룹명 (스냅샷) |
| optionName | String | NOT NULL | 옵션명 (스냅샷) |
| additionalPrice | Integer | NOT NULL | 추가 금액 (스냅샷) |

### OrderHistory
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, auto | 이력 ID |
| tableId | Long | FK(TableInfo), NOT NULL | 테이블 |
| sessionId | Long | NOT NULL | 원본 세션 ID |
| orderNumber | String | NOT NULL | 주문 번호 |
| orderData | TEXT (JSON) | NOT NULL | 주문 전체 데이터 (JSON 스냅샷) |
| totalAmount | Integer | NOT NULL | 총 금액 |
| orderedAt | LocalDateTime | NOT NULL | 주문 시각 |
| completedAt | LocalDateTime | NOT NULL | 이용 완료 시각 |
