# Code Generation Plan - Frontend Unit

## Unit Context
- **Unit**: frontend
- **Path**: `frontend/`
- **Tech**: React 18, TypeScript, Zustand, Vite, React Router v6
- **Stories**: 34개 (전체 MVP)

## Plan Steps

### Step 1: 프로젝트 구조 및 설정
- [x] Vite + React + TypeScript 프로젝트 설정 (package.json, vite.config.ts, tsconfig.json)
- [x] 디렉토리 구조 생성
- [x] 라우팅 설정 (App.tsx + React Router)

### Step 2: 공통 타입 및 서비스
- [x] types/ (Store, Menu, Category, Option, Order, Table 등 타입 정의)
- [x] services/api.ts (API 클라이언트, base URL, 에러 처리)
- [x] services/sse.ts (SSE 구독 유틸)

### Step 3: Zustand 스토어
- [x] stores/useAuthStore.ts (역할, 매장, 테이블 인증 정보)
- [x] stores/useCartStore.ts (장바구니, localStorage 연동)
- [x] stores/useOrderStore.ts (주문 목록, SSE 연동)
- [x] Unit Tests (useCartStore)

### Step 4: 공통 Atoms 컴포넌트
- [x] Button, Input, Badge, Modal, Loading 등 기본 UI
- [x] Unit Tests (Button)

### Step 5: 공통 레이아웃 (Templates)
- [x] CustomerLayout, StoreAdminLayout, SuperAdminLayout
- [x] 네비게이션 구조

### Step 6: 고객 페이지 (Customer)
- [x] TableSetupPage (태블릿 초기 설정, US-C02)
- [x] MenuPage (카테고리 탭 + 메뉴 그리드, US-C01/C03/C04)
- [x] MenuDetailModal (옵션 선택 + 장바구니 추가, US-C04-1/C05)
- [x] CartPage (수량 조절/삭제/비우기, US-C06/C07/C08)
- [x] OrderConfirmPage (주문 확정, US-C09)
- [x] OrderHistoryPage (SSE 실시간 상태, US-C10)
- [x] Unit Tests (MenuPage)

### Step 7: 매장 관리자 페이지 (Store Admin)
- [x] DashboardPage (테이블 카드 그리드 + SSE, US-A02/A05/A07/A09)
- [x] TableDetailModal (주문 상세/상태변경/삭제, US-A03/A04/A06)
- [x] OrderHistoryArchivePage (과거 이력, US-A08)
- [x] Unit Tests (DashboardPage)

### Step 8: 슈퍼 관리자 페이지 (Super Admin)
- [x] StoreListPage / StoreFormPage (매장 CRUD, US-S02/S03/S04/S05)
- [x] AdminAccountPage (관리자 계정 CRUD, US-S06/S07/S08)
- [x] CategoryPage (카테고리 CRUD, US-S09-1)
- [x] MenuListPage / MenuFormPage (메뉴 CRUD + 옵션 관리, US-S09-2~S09-7)
- [x] Unit Tests (StoreListPage)

### Step 9: 역할 선택 페이지
- [x] RoleSelectPage (US-A01/S01)

### Step 10: Frontend 코드 요약 문서
- [x] `aidlc-docs/construction/frontend/code/frontend-code-summary.md`
