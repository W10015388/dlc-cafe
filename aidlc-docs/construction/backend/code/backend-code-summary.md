# Backend Code Summary

## 생성된 파일 목록

### 프로젝트 설정
- `backend/build.gradle.kts` - Gradle 빌드 설정
- `backend/settings.gradle.kts` - 프로젝트 설정
- `backend/src/main/resources/application.yml` - 앱 설정
- `backend/src/main/resources/data.sql` - Seed 데이터
- `backend/src/main/java/com/tableorder/TableOrderApplication.java` - 메인 클래스

### 공통 (common)
- `common/dto/ErrorResponse.java` - 에러 응답 DTO
- `common/exception/NotFoundException.java` - 404 예외
- `common/exception/BusinessException.java` - 400 예외
- `common/config/GlobalExceptionHandler.java` - 글로벌 예외 핸들러
- `common/config/WebConfig.java` - CORS, 정적 리소스

### 도메인별 (7개 도메인)
각 도메인: Entity → Repository → Service → Controller → DTO

| 도메인 | 엔티티 | API 경로 |
|--------|--------|----------|
| store | Store | /api/v1/stores |
| storeadmin | StoreAdmin | /api/v1/store-admins |
| category | Category | /api/v1/categories |
| menu | Menu, OptionGroup, Option | /api/v1/menus |
| table | TableInfo, TableSession | /api/v1/tables, /api/v1/auth/table |
| order | Order, OrderItem, OrderItemOption | /api/v1/orders |
| orderhistory | OrderHistory | /api/v1/order-histories |

### 인프라
- `file/application/FileStorageService.java` - 파일 업로드
- `sse/application/SseEventService.java` - SSE 이벤트 Pub/Sub
- `sse/adapter/in/web/SseController.java` - SSE 엔드포인트

### 테스트 (7개)
- StoreServiceTest, StoreAdminServiceTest, CategoryServiceTest
- MenuServiceTest, TableServiceTest, OrderServiceTest
- OrderHistoryServiceTest, FileStorageServiceTest, SseEventServiceTest

## 총 파일 수: ~50개 (소스 + 테스트)
