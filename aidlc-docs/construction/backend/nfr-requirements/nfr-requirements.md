# NFR Requirements - Backend

## NFR-01: 성능
- API 응답 시간: 1초 이내 (일반 CRUD)
- SSE 이벤트 전달: 2초 이내 (요구사항 기준)
- 동시 접속: 1개 매장, 테이블 10개 이하 (MVP)
- DB 쿼리: 인덱스 기반 조회 (FK 컬럼에 인덱스)

## NFR-02: 에러 처리
- HTTP 상태 코드 + 한국어 메시지 응답
- 응답 형식: `{ "status": 400, "message": "필수 옵션을 선택해주세요" }`
- 400 (검증 실패), 404 (리소스 없음), 409 (상태 충돌), 500 (서버 에러)

## NFR-03: 로깅
- Spring Boot 기본 로깅 (Logback)
- 에러 발생 시 스택 트레이스 포함
- 추가 커스텀 로깅 없음 (MVP)

## NFR-04: 데이터 검증
- 프론트엔드: 사용자 입력 즉시 검증 (UX)
- 백엔드: Bean Validation (@NotNull, @Min, @Size 등) 으로 이중 검증
- 서버 측 가격 재계산 (클라이언트 금액 불신)

## NFR-05: 데이터베이스
- H2 파일 모드 (앱 재시작 시 데이터 유지)
- 시작 시 seed data 자동 삽입 (기본 카테고리 6개, 샘플 매장/메뉴)
- schema.sql + data.sql 활용

## NFR-06: 파일 업로드
- 메뉴 이미지: 최대 5MB
- 허용 형식: JPG, PNG
- 저장 경로: `./uploads/images/`
- 파일명: UUID 기반 (충돌 방지)

## NFR-07: SSE (Server-Sent Events)
- Spring WebFlux Flux<ServerSentEvent> 사용
- 매장별 이벤트 스트림 (storeId 기반 구독)
- 테이블별 이벤트 스트림 (tableId 기반 구독)
- 연결 끊김 시 클라이언트 자동 재연결 (EventSource 기본 동작)

## NFR-08: CORS
- 개발 환경: 모든 origin 허용 (`*`)
- 프론트엔드 기본 포트: 5173 (Vite)
- 백엔드 기본 포트: 8080
