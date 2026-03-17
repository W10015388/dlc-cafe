# TableOrder 프로젝트 전체 작업 히스토리 (2026-03-17)

---

## Part 1. INCEPTION PHASE (설계)

### 1. Workspace Detection (11:11)
- 사용자 요청: "테이블오더 서비스를 구축하고 싶습니다"
- requirements/table-order-requirements.md, requirements/constraints.md 로드
- Greenfield 프로젝트로 판별 (기존 소스코드 없음)

### 2. Requirements Analysis (11:11~11:28)
- 인터뷰 형식으로 11개 질문 진행
  - Q1: Java + Spring Boot (A)
  - Q2: React (A)
  - Q3: PostgreSQL → 이후 H2로 변경
  - Q4: 배포 환경 나중에 결정, 로컬 우선 (D)
  - Q5: MVP 단계 규모 고려 불필요 (D)
  - Q6: 슈퍼 관리자 + 매장 관리자 계층 구조 (C)
  - Q7: 로컬 파일 시스템에 이미지 저장 (D)
  - Q8: MVP에 메뉴 관리 CRUD 포함 (A)
  - Q9: 매장 관리자가 태블릿 초기 설정 시 인증 (A)
  - Q10: SSE로 실시간 주문 상태 반영 (A)
  - Q11: 보안 규칙 건너뛰기 (B)
- 변경 요청: "관리자용 매장 인증 빼고, 역할 선택만으로 바로 진입"
- 승인

### 3. User Stories (11:28~13:07)
- 3개 질문 인터뷰: Task 수준 스토리(C), 상세 AC(C), 슈퍼 관리자 전체 기능(C)
- 3개 페르소나, 34개 스토리 생성 (고객 10, 매장 관리자 14, 슈퍼 관리자 10)
- 변경 요청: "카페 컨셉으로, 커스텀 주문 옵션 넣어서 커피 주문하는 프리미엄 카페"
- DB 변경: PostgreSQL → H2 (파일 모드)
- 승인

### 4. Workflow Planning (13:07~13:19)
- 실행 계획 수립: Application Design → Units Generation → Functional Design → NFR → Code Generation → Build & Test
- 스킵: Reverse Engineering, Infrastructure Design
- 리뷰 요청 → 7개 피드백 반영 (JWT 제거, 카테고리 관리 추가, MVP 범위 축소 등)
- 승인

### 5. Application Design (13:19~13:25)
- 4개 질문 인터뷰
  - Q1: 헥사고날 아키텍처 (C)
  - Q2: Atomic Design (C)
  - Q3: 모노레포 backend/frontend 분리 (A)
  - Q4: Spring WebFlux 기반 SSE (A)
- 7개 도메인 컴포넌트 + 3개 인프라 컴포넌트 식별
- 승인

### 6. Units Generation (13:25~13:32)
- Backend/Frontend 2개 Unit 병렬 개발 (C)
- 34개 MVP 스토리 매핑 완료
- 승인

---

## Part 2. CONSTRUCTION PHASE (구현)

### 7. Functional Design (13:32~13:43)
- 3개 질문 인터뷰
  - Q1: 주문 상태 순방향만 허용 (대기중→준비중→완료) (A)
  - Q2: 세션 만료 없음, 관리자 이용 완료 시에만 종료 (A)
  - Q3: 상태관리 Zustand (B)
- Backend: 12개 엔티티, 11개 비즈니스 규칙, 3개 핵심 플로우
- Frontend: 3개 Zustand store, 페이지별 컴포넌트, 라우팅 구조

### 8. NFR Requirements (13:43~13:48)
- "알아서 잘 해봐. 어차피 MVP야" → AI가 MVP 기준으로 자체 결정
- 8개 NFR, 기술 스택 확정
- 승인

### 9. NFR Design (13:48)
- 6개 패턴: 글로벌 예외처리, Bean Validation, SSE Sink, 트랜잭션, 파일 업로드, Seed Data
- 20개 API 엔드포인트 정의

### 10. Code Generation (13:51~14:13)
- Backend: 13 Steps, ~50개 파일 (7개 도메인 + 공통 + 인프라 + 테스트 9개)
- Frontend: 10 Steps, ~30개 파일 (15 페이지, 4 atoms, 3 templates, 3 stores, 3 services, 7 tests)
- 승인

### 11. Build & Test (14:14)
- 빌드/테스트 문서 5개 생성 완료

---

## Part 3. 버그 수정 및 개선 (15:18~16:02, Kiro CLI)

### 12. 고객 버튼 → 바로 메뉴 화면 이동
- 기존: 고객 버튼 → TableSetupPage (매장코드/테이블번호/비밀번호 입력)
- 변경: 고객 버튼 → 첫 번째 매장 1번 테이블로 자동 인증 → 메뉴 화면 바로 이동
- 수정 파일:
  - `backend/.../TableService.java` — 비밀번호 검증 로직 제거
  - `backend/.../TableAuthRequest.java` — password 필드 optional
  - `frontend/.../RoleSelectPage.tsx` — 고객 버튼 클릭 시 자동 인증

### 13. 메뉴 목록 500 에러 수정
- 원인: WebFlux 환경에서 JPA LAZY fetch → LazyInitializationException
- 수정 파일:
  - `backend/.../Menu.java` — optionGroups LAZY → EAGER
  - `backend/.../OptionGroup.java` — options LAZY → EAGER

### 14. 주문 확정 에러 수정 (orderNumber unique 제약 위반)
- 원인: AtomicLong 카운터가 서버 재시작마다 0으로 리셋 → DB 데이터와 주문번호 충돌
- 수정 파일:
  - `backend/.../OrderService.java` — 주문번호를 UUID 기반(`ORD-yyyyMMdd-XXXXXX`)으로 변경

### 15. 주문 확정 에러 수정 (ORDER_ITEM_ID NULL)
- 원인: OrderItem이 DB에 save되기 전(ID 없음)에 OrderItemOption 생성 → orderItemId NULL
- 수정 파일:
  - `backend/.../OrderItemRepository.java` — 신규 생성
  - `backend/.../OrderService.java` — OrderItem 먼저 save하여 ID 확보 후 옵션 추가
  - `backend/.../Order.java` — items EAGER fetch, updateTotalAmount 추가
  - `backend/.../OrderItem.java` — options EAGER fetch

### 16. 에러 메시지 노출
- 기존: GlobalExceptionHandler가 500 에러를 "서버 오류가 발생했습니다"로 숨김
- 변경: 실제 에러 메시지 노출 + 스택트레이스 출력
- 수정 파일: `backend/.../GlobalExceptionHandler.java`

### 17. localStorage 잔존 데이터 제거
- 기존: zustand persist가 auth-storage, cart-storage를 localStorage에 저장
- 변경: persist 제거 → 새로고침 시 상태 초기화
- 수정 파일:
  - `frontend/.../useAuthStore.ts`
  - `frontend/.../useCartStore.ts`

### 18. MenuPage 리다이렉트 경로 수정
- 기존: tableId 없으면 `/customer/setup`으로 이동
- 변경: `/`(역할 선택 페이지)로 이동
- 수정 파일: `frontend/.../MenuPage.tsx`

### 19. 메뉴 썸네일 이미지 교체
- Unsplash에서 무료 커피 이미지 9장 다운로드하여 교체
- 경로: `frontend/public/images/menu/coffee-1~9.png`

---

## 전체 수정 파일 목록

### Backend
| 파일 | 변경 내용 |
|------|----------|
| `TableService.java` | 비밀번호 검증 제거 |
| `TableAuthRequest.java` | password optional |
| `Menu.java` | optionGroups EAGER fetch |
| `OptionGroup.java` | options EAGER fetch |
| `Order.java` | items EAGER fetch, updateTotalAmount 추가 |
| `OrderItem.java` | options EAGER fetch |
| `OrderService.java` | 주문번호 UUID 기반, OrderItem 선 save 후 옵션 추가 |
| `OrderRepository.java` | countByOrderNumberStartingWith 추가 |
| `OrderItemRepository.java` | 신규 생성 |
| `GlobalExceptionHandler.java` | 실제 에러 메시지 노출 |

### Frontend
| 파일 | 변경 내용 |
|------|----------|
| `RoleSelectPage.tsx` | 고객 버튼 → 자동 인증 |
| `MenuPage.tsx` | 리다이렉트 경로 수정 |
| `useAuthStore.ts` | persist 제거 |
| `useCartStore.ts` | persist 제거 |
| `public/images/menu/*` | 커피 이미지 교체 |
