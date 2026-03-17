# Application Design - 통합 문서

## 프로젝트 구조

```
table-order/
+-- backend/                          (Spring Boot + WebFlux)
|   +-- src/main/java/com/tableorder/
|   |   +-- store/
|   |   |   +-- domain/               (Entity, Port)
|   |   |   +-- application/          (Service - UseCase 구현)
|   |   |   +-- adapter/
|   |   |       +-- in/web/           (Controller, DTO)
|   |   |       +-- out/persistence/  (JPA Entity, Repository 구현)
|   |   +-- storeadmin/               (동일 구조)
|   |   +-- category/                 (동일 구조)
|   |   +-- menu/                     (동일 구조)
|   |   +-- table/                    (동일 구조)
|   |   +-- order/                    (동일 구조)
|   |   +-- orderhistory/             (동일 구조)
|   |   +-- file/                     (FileStorage adapter)
|   |   +-- sse/                      (SSE adapter - WebFlux)
|   |   +-- common/                   (공통 설정, 예외 처리)
|   +-- src/main/resources/
|       +-- application.yml
+-- frontend/                         (React + Atomic Design)
|   +-- src/
|   |   +-- components/
|   |   |   +-- atoms/
|   |   |   +-- molecules/
|   |   |   +-- organisms/
|   |   |   +-- templates/
|   |   +-- pages/
|   |   |   +-- customer/
|   |   |   +-- store-admin/
|   |   |   +-- super-admin/
|   |   |   +-- common/
|   |   +-- services/                 (API, SSE, Cart)
|   |   +-- hooks/
|   |   +-- types/
|   +-- package.json
```

## 아키텍처 결정 요약

| 항목 | 결정 |
|------|------|
| Backend 아키텍처 | 헥사고날 (Port & Adapter) |
| Frontend 아키텍처 | Atomic Design |
| 프로젝트 배치 | 모노레포 (backend/ + frontend/) |
| SSE 구현 | Spring WebFlux (Reactive) |
| DB | H2 파일 모드 |
| 이미지 저장 | 로컬 파일 시스템 |
| 메뉴/카테고리 관리 | 전체 체인 공통 (슈퍼 관리자) |

## 도메인 컴포넌트 (7개)

1. **Store** — 매장 관리
2. **StoreAdmin** — 매장 관리자 계정
3. **Category** — 메뉴 카테고리 (체인 공통)
4. **Menu** — 메뉴 + 커스텀 옵션 (체인 공통)
5. **Table** — 테이블 + 세션 관리
6. **Order** — 주문 처리 + 상태 관리
7. **OrderHistory** — 과거 주문 아카이빙

## 인프라 컴포넌트 (3개)

8. **FileStorage** — 로컬 파일 시스템 이미지 저장
9. **SSEEvent** — WebFlux 기반 실시간 이벤트
10. **JPA Persistence** — H2 데이터베이스 영속성

## 핵심 데이터 흐름

- 고객 주문: UI → REST → OrderService → DB 저장 + SSE 이벤트 발행 → 관리자 대시보드 실시간 반영
- 상태 변경: 관리자 UI → REST → OrderService → DB 업데이트 + SSE → 고객 화면 실시간 반영
- 이용 완료: 관리자 UI → REST → TableService → OrderHistoryService (아카이빙) → 테이블 리셋
- 세션 시작: 첫 주문 시 OrderService가 자동으로 새 세션 생성
