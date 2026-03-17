# Business Logic Model - Backend

## 핵심 비즈니스 플로우

### Flow 1: 고객 주문 플로우
```
1. 태블릿 인증 (storeCode + tableNumber + password)
2. 메뉴 조회 (카테고리별, 옵션 포함)
3. 주문 생성 요청
   3.1 메뉴/옵션 유효성 검증
   3.2 필수 옵션 선택 여부 검증
   3.3 가격 계산 (서버 측 재계산)
   3.4 활성 세션 확인 → 없으면 새 세션 생성
   3.5 주문 번호 생성
   3.6 주문 저장
   3.7 SSE 이벤트 발행 (NEW_ORDER)
4. 주문 내역 조회 (현재 세션만)
5. SSE로 주문 상태 실시간 수신
```

### Flow 2: 관리자 주문 관리 플로우
```
1. 역할 선택 → 매장 선택 → 대시보드 진입
2. SSE 구독 (매장 전체 주문 이벤트)
3. 주문 상태 변경
   3.1 현재 상태 확인
   3.2 순방향 전이 검증 (PENDING→PREPARING→COMPLETED)
   3.3 상태 업데이트
   3.4 SSE 이벤트 발행 (STATUS_CHANGED)
4. 주문 삭제
   4.1 주문 삭제
   4.2 테이블 총 주문액 재계산
   4.3 SSE 이벤트 발행 (ORDER_DELETED)
5. 이용 완료
   5.1 활성 세션 확인
   5.2 세션의 모든 주문 → OrderHistory JSON 스냅샷
   5.3 세션 비활성화 (active=false, completedAt 기록)
   5.4 SSE 이벤트 발행 (SESSION_COMPLETED)
```

### Flow 3: 슈퍼 관리자 메뉴 관리 플로우
```
1. 역할 선택 → 슈퍼 관리자 대시보드
2. 카테고리 CRUD (전체 체인 공통)
3. 메뉴 CRUD (전체 체인 공통)
   3.1 메뉴 등록 시 이미지 업로드 (선택)
   3.2 옵션 그룹/옵션 관리
4. 매장 CRUD
5. 매장 관리자 계정 관리
```

## SSE 이벤트 타입

| Event Type | Payload | 수신 대상 |
|-----------|---------|----------|
| NEW_ORDER | orderId, tableNumber, items, totalAmount | 매장 관리자 (해당 매장) |
| STATUS_CHANGED | orderId, newStatus | 고객 (해당 테이블), 매장 관리자 |
| ORDER_DELETED | orderId, tableNumber | 고객 (해당 테이블), 매장 관리자 |
| SESSION_COMPLETED | tableId | 고객 (해당 테이블) |

## 가격 계산 로직

```
orderItemPrice = menu.price + SUM(selectedOptions.additionalPrice)
orderItemTotal = orderItemPrice × quantity
orderTotal = SUM(orderItemTotal for each item)
```

서버 측에서 항상 재계산 (클라이언트 전송 금액은 참고용, 서버 계산 결과가 최종)
