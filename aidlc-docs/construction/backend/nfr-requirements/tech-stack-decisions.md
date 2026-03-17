# Tech Stack Decisions - Backend

## 확정 기술 스택

| 영역 | 기술 | 버전 | 선택 이유 |
|------|------|------|----------|
| Language | Java | 17 | Spring Boot 3.x LTS 호환 |
| Framework | Spring Boot | 3.x | WebFlux SSE 지원, 생산성 |
| Reactive | Spring WebFlux | (Boot 포함) | SSE 스트리밍, 비동기 이벤트 |
| Database | H2 | (Boot 포함) | 로컬 개발, 설치 불필요, 파일 모드 |
| ORM | Spring Data JPA + Hibernate | (Boot 포함) | 표준 JPA, 생산성 |
| Validation | Bean Validation (Hibernate Validator) | (Boot 포함) | 선언적 검증 |
| Build | Gradle (Kotlin DSL) | 8.x | 의존성 관리, 빌드 속도 |
| API Docs | 없음 (MVP) | - | 코드 우선, 문서는 추후 |

## Frontend 기술 스택

| 영역 | 기술 | 선택 이유 |
|------|------|----------|
| Framework | React 18 | 컴포넌트 기반, 생태계 |
| Language | TypeScript | 타입 안전성 |
| State | Zustand | 경량, 간단한 API |
| Build | Vite | 빠른 HMR, 개발 편의 |
| HTTP | fetch API | 추가 라이브러리 불필요 |
| Routing | React Router v6 | 표준 라우팅 |
| Styling | CSS Modules 또는 Tailwind (코드 생성 시 결정) | MVP 적합 |

## 주요 결정 사항

1. **WebFlux 혼합 사용**: SSE 엔드포인트만 WebFlux (Flux<SSE>), 나머지 REST는 Spring MVC 스타일 (spring-boot-starter-webflux 사용 시 전체 reactive)
   - 결정: `spring-boot-starter-webflux` 사용 (전체 reactive stack). 단, 서비스 로직은 블로킹 스타일로 간단하게 작성
2. **H2 파일 경로**: `./data/tableorder` (프로젝트 루트 기준)
3. **이미지 저장 경로**: `./uploads/images/` (프로젝트 루트 기준)
4. **Seed Data**: `src/main/resources/data.sql`로 기본 카테고리 6개 + 샘플 매장 1개 삽입
5. **API 경로 규칙**: `/api/v1/` prefix 사용
