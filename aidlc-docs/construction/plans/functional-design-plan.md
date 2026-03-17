# Functional Design Plan

## Design Decisions
- 주문 상태 전이: 순방향만 (PENDING→PREPARING→COMPLETED)
- 세션 만료: 없음 (관리자 이용 완료 처리 시에만 종료)
- Frontend 상태 관리: Zustand

## Execution Plan
- [x] Step 1: Backend 도메인 엔티티 설계
- [x] Step 2: Backend 비즈니스 규칙 정의
- [x] Step 3: Backend 비즈니스 로직 모델
- [x] Step 4: Frontend 컴포넌트 설계
