# Unit Test Execution

## Backend Unit Tests

### 실행
```bash
cd backend
./gradlew test
```

### 테스트 목록 (9개 테스트 클래스)
| 클래스 | 테스트 수 | 대상 |
|--------|----------|------|
| StoreServiceTest | 5 | 매장 CRUD, 중복 코드 검증 |
| StoreAdminServiceTest | 3 | 관리자 생성, 중복 사용자명 |
| CategoryServiceTest | 3 | 카테고리 CRUD, 중복명 |
| MenuServiceTest | 3 | 메뉴 CRUD |
| FileStorageServiceTest | 2 | 파일 업로드, 형식 검증 |
| TableServiceTest | 3 | 인증, 세션 생성, 이용 완료 |
| OrderServiceTest | 4 | 주문 상태 전이, 순방향만 |
| OrderHistoryServiceTest | 2 | 아카이빙, 빈 세션 |
| SseEventServiceTest | 2 | SSE 구독/발행 |

### 결과 확인
- 리포트: `build/reports/tests/test/index.html`
- 예상: 27개 테스트 전체 통과

### 실패 시
1. `build/reports/tests/test/index.html` 열어서 실패 테스트 확인
2. 에러 메시지 확인 후 코드 수정
3. `./gradlew test` 재실행

## Frontend Unit Tests

### 실행
```bash
cd frontend
npm run test
```

> package.json에 test 스크립트 추가 필요:
> ```json
> "test": "vitest run",
> "test:watch": "vitest"
> ```

### 테스트 목록 (2개 테스트 파일)
| 파일 | 테스트 수 | 대상 |
|------|----------|------|
| useCartStore.test.ts | 5 | 장바구니 추가/병합/수량/삭제/비우기 |
| Button.test.tsx | 2 | 렌더링, variant 클래스 |

### 결과 확인
- 콘솔 출력으로 확인
- 예상: 7개 테스트 전체 통과
