# Frontend Code Summary

## 생성된 파일 목록

### 프로젝트 설정
- `frontend/package.json`, `vite.config.ts`, `tsconfig.json`, `tsconfig.node.json`
- `frontend/index.html`, `src/main.tsx`, `src/App.tsx`, `src/index.css`

### 타입 및 서비스
- `src/types/index.ts` - 전체 타입 정의
- `src/services/api.ts` - API 클라이언트 (GET/POST/PUT/PATCH/DELETE/upload)
- `src/services/sse.ts` - SSE 구독 유틸

### Zustand 스토어
- `src/stores/useAuthStore.ts` - 인증/역할 상태 (localStorage)
- `src/stores/useCartStore.ts` - 장바구니 (localStorage)
- `src/stores/useOrderStore.ts` - 주문 목록

### Atoms
- `src/components/atoms/Button.tsx`, `Badge.tsx`, `Modal.tsx`, `Loading.tsx`

### Templates (레이아웃)
- `src/components/templates/CustomerLayout.tsx`
- `src/components/templates/StoreAdminLayout.tsx`
- `src/components/templates/SuperAdminLayout.tsx`

### 고객 페이지 (6개)
- `TableSetupPage.tsx` - 태블릿 초기 설정
- `MenuPage.tsx` - 카테고리별 메뉴 + 옵션 선택 모달
- `CartPage.tsx` - 장바구니 관리
- `OrderConfirmPage.tsx` - 주문 확정
- `OrderHistoryPage.tsx` - 주문 내역 (SSE 실시간)

### 매장 관리자 페이지 (2개)
- `DashboardPage.tsx` - 테이블 대시보드 + 주문 상세 모달 + 테이블 추가
- `OrderHistoryArchivePage.tsx` - 과거 이력

### 슈퍼 관리자 페이지 (6개)
- `StoreListPage.tsx`, `StoreFormPage.tsx` - 매장 CRUD
- `AdminAccountPage.tsx` - 관리자 계정
- `CategoryPage.tsx` - 카테고리 CRUD
- `MenuListPage.tsx`, `MenuFormPage.tsx` - 메뉴 CRUD

### 공통 페이지 (1개)
- `RoleSelectPage.tsx` - 역할 선택

### 테스트
- `src/test/setup.ts`
- `src/test/stores/useCartStore.test.ts` (5개 테스트)
- `src/test/components/Button.test.tsx` (2개 테스트)

## 총 파일 수: ~30개
## 라우트: 17개
## data-testid: 모든 인터랙티브 요소에 적용
