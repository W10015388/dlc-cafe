# NFR Design Patterns - Backend

## 1. 글로벌 예외 처리 패턴

### GlobalExceptionHandler (@RestControllerAdvice)
- 모든 예외를 통일된 JSON 형식으로 응답
- 응답 형식: `{ "status": int, "message": String }`
- 커스텀 예외 클래스:
  - `NotFoundException` → 404
  - `BusinessException` → 400 (검증 실패, 상태 전이 오류 등)
  - `Exception` (기타) → 500

```java
// 예외 응답 구조
public record ErrorResponse(int status, String message) {}
```

## 2. 입력 검증 패턴

### Bean Validation + DTO 분리
- Request DTO에 `@NotNull`, `@Min`, `@Size` 등 선언적 검증
- `@Valid` 어노테이션으로 컨트롤러 진입 시 자동 검증
- MethodArgumentNotValidException → GlobalExceptionHandler에서 처리

## 3. SSE 이벤트 발행 패턴

### 인메모리 Sink 기반 Pub/Sub
- `Sinks.Many<ServerSentEvent>` 사용
- 매장별 Sink: `Map<Long, Sinks.Many<SSE>>` (storeId 기반)
- 테이블별 Sink: `Map<Long, Sinks.Many<SSE>>` (tableId 기반)
- 구독 해제 시 자동 정리 (doOnCancel)
- 이벤트 타입: NEW_ORDER, STATUS_CHANGED, ORDER_DELETED, SESSION_COMPLETED

```
SseEventService
  ├── subscribeStore(storeId) → Flux<SSE>
  ├── subscribeTable(tableId) → Flux<SSE>
  ├── publishToStore(storeId, event)
  └── publishToTable(tableId, event)
```

## 4. 도메인 서비스 패턴

### 트랜잭션 경계
- 각 서비스 메서드 = 1 트랜잭션 (@Transactional)
- 주문 생성: 세션 확인/생성 + 주문 저장 + SSE 발행 (하나의 트랜잭션)
- 이용 완료: 주문 아카이빙 + 세션 비활성화 (하나의 트랜잭션)

### 가격 계산
- 서버 측 재계산 전용 (클라이언트 금액 무시)
- OrderService.calculatePrice() 내부에서 Menu + Option 조회 후 계산

## 5. 파일 업로드 패턴

### UUID 기반 파일 저장
- 원본 파일명 → UUID + 확장자로 변환
- 저장 경로: `./uploads/images/{uuid}.{ext}`
- 정적 리소스 서빙: WebFlux ResourceHandler로 `/uploads/**` 매핑
- 파일 크기/형식 검증: 컨트롤러 레벨에서 수행

## 6. Seed Data 패턴

### Spring Boot data.sql
- `spring.jpa.defer-datasource-initialization=true` 설정
- `spring.sql.init.mode=always` (H2 파일 모드에서도 동작)
- 조건부 삽입: `MERGE INTO` 또는 앱 시작 시 존재 여부 체크
- 기본 데이터: 카테고리 6개, 샘플 매장 1개
