# Unit of Work - Story Map

## Backend Unit

| Story ID | Story Name | 관련 도메인 |
|----------|-----------|------------|
| US-C01 | 테이블 태블릿 초기 화면 진입 | Table |
| US-C02 | 테이블 태블릿 초기 설정 | Table |
| US-C03 | 카테고리별 메뉴 목록 조회 | Menu, Category |
| US-C04 | 메뉴 상세 정보 조회 | Menu |
| US-C04-1 | 커스텀 옵션 선택 | Menu |
| US-C05 | 장바구니에 메뉴 추가 | (API: Menu 조회) |
| US-C09 | 주문 내역 확인 및 주문 확정 | Order, Table |
| US-C10 | 주문 내역 목록 조회 | Order, SSE |
| US-A01 | 관리자 역할 선택 진입 | Store |
| US-A02 | 실시간 주문 대시보드 조회 | Order, SSE |
| US-A03 | 주문 상세 보기 | Order |
| US-A04 | 주문 상태 변경 | Order, SSE |
| US-A05 | 테이블별 주문 필터링 | Order |
| US-A06 | 주문 삭제 | Order |
| US-A07 | 테이블 이용 완료 처리 | Table, OrderHistory |
| US-A08 | 과거 주문 내역 조회 | OrderHistory |
| US-A09 | 테이블 초기 설정 | Table |
| US-S01 | 슈퍼 관리자 역할 선택 진입 | (API: 역할 분기) |
| US-S02 | 매장 등록 | Store |
| US-S03 | 매장 목록 조회 | Store |
| US-S04 | 매장 정보 수정 | Store |
| US-S05 | 매장 삭제 | Store |
| US-S06 | 매장 관리자 계정 생성 | StoreAdmin |
| US-S07 | 매장 관리자 계정 목록 조회 | StoreAdmin |
| US-S08 | 매장 관리자 계정 삭제 | StoreAdmin |
| US-S09-1 | 카테고리 관리 | Category |
| US-S09-2 | 메뉴 목록 조회 | Menu |
| US-S09-3 | 메뉴 등록 | Menu, File |
| US-S09-4 | 메뉴 커스텀 옵션 그룹 관리 | Menu |
| US-S09-5 | 메뉴 수정 | Menu, File |
| US-S09-6 | 메뉴 삭제 | Menu |
| US-S09-7 | 메뉴 노출 순서 조정 | Menu |

## Frontend Unit

| Story ID | Story Name | 관련 페이지 |
|----------|-----------|------------|
| US-C01 | 테이블 태블릿 초기 화면 진입 | MenuPage / TableSetupPage |
| US-C02 | 테이블 태블릿 초기 설정 | TableSetupPage |
| US-C03 | 카테고리별 메뉴 목록 조회 | MenuPage |
| US-C04 | 메뉴 상세 정보 조회 | MenuPage (상세 모달) |
| US-C04-1 | 커스텀 옵션 선택 | MenuPage (옵션 선택 모달) |
| US-C05 | 장바구니에 메뉴 추가 | CartPanel |
| US-C06 | 장바구니 수량 조절 | CartPage |
| US-C07 | 장바구니 메뉴 삭제 | CartPage |
| US-C08 | 장바구니 비우기 | CartPage |
| US-C09 | 주문 내역 확인 및 주문 확정 | OrderConfirmPage |
| US-C10 | 주문 내역 목록 조회 | OrderHistoryPage |
| US-A01 | 관리자 역할 선택 진입 | RoleSelectPage |
| US-A02 | 실시간 주문 대시보드 조회 | DashboardPage |
| US-A03 | 주문 상세 보기 | TableDetailPage |
| US-A04 | 주문 상태 변경 | TableDetailPage |
| US-A05 | 테이블별 주문 필터링 | DashboardPage |
| US-A06 | 주문 삭제 | TableDetailPage |
| US-A07 | 테이블 이용 완료 처리 | DashboardPage |
| US-A08 | 과거 주문 내역 조회 | OrderHistoryArchivePage |
| US-A09 | 테이블 초기 설정 | DashboardPage (설정 모달) |
| US-S01 | 슈퍼 관리자 역할 선택 진입 | RoleSelectPage |
| US-S02 | 매장 등록 | StoreFormPage |
| US-S03 | 매장 목록 조회 | StoreListPage |
| US-S04 | 매장 정보 수정 | StoreFormPage |
| US-S05 | 매장 삭제 | StoreListPage |
| US-S06 | 매장 관리자 계정 생성 | AdminAccountPage |
| US-S07 | 매장 관리자 계정 목록 조회 | AdminAccountPage |
| US-S08 | 매장 관리자 계정 삭제 | AdminAccountPage |
| US-S09-1 | 카테고리 관리 | CategoryPage |
| US-S09-2 | 메뉴 목록 조회 | MenuListPage |
| US-S09-3 | 메뉴 등록 | MenuFormPage |
| US-S09-4 | 메뉴 커스텀 옵션 그룹 관리 | MenuFormPage |
| US-S09-5 | 메뉴 수정 | MenuFormPage |
| US-S09-6 | 메뉴 삭제 | MenuListPage |
| US-S09-7 | 메뉴 노출 순서 조정 | MenuListPage |

## Coverage Summary

- **전체 MVP 스토리**: 34개 활성
- **Backend 매핑**: 32개 (장바구니 로컬 스토리 US-C06, C07, C08 제외)
- **Frontend 매핑**: 34개 (전체)
- **미매핑 스토리**: 0개
