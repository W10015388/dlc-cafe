# Build Instructions

## Prerequisites
- **Java**: JDK 17+
- **Node.js**: 18+ (npm 포함)
- **OS**: macOS / Linux / Windows

## Backend Build

### 1. 의존성 설치 및 빌드
```bash
cd backend
./gradlew build
```

> Gradle Wrapper가 없는 경우:
> ```bash
> gradle wrapper
> ./gradlew build
> ```

### 2. 실행
```bash
./gradlew bootRun
```
- 서버: http://localhost:8080
- H2 콘솔: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:file:./data/tableorder`)
- Seed data 자동 삽입 (카테고리 6개, 샘플 매장 1개, 테이블 3개, 메뉴 4개)

### 3. 빌드 확인
- `build/libs/tableorder-backend-0.0.1-SNAPSHOT.jar` 생성 확인
- 콘솔에 `Started TableOrderApplication` 메시지 확인

## Frontend Build

### 1. 의존성 설치
```bash
cd frontend
npm install
```

### 2. 개발 서버 실행
```bash
npm run dev
```
- 서버: http://localhost:5173
- API 프록시: `/api` → `http://localhost:8080` (vite.config.ts)

### 3. 프로덕션 빌드
```bash
npm run build
```
- 출력: `dist/` 디렉토리

## 전체 실행 순서
1. Backend 먼저 실행 (`./gradlew bootRun`)
2. Frontend 실행 (`npm run dev`)
3. 브라우저에서 http://localhost:5173 접속

## Troubleshooting

### Gradle 빌드 실패
- JDK 17 설치 확인: `java -version`
- Gradle 캐시 초기화: `./gradlew clean build --refresh-dependencies`

### npm install 실패
- Node.js 18+ 확인: `node -v`
- 캐시 초기화: `rm -rf node_modules && npm install`

### H2 DB 파일 잠금 오류
- 이전 프로세스 종료 확인
- `./data/tableorder.mv.db.lock` 파일 삭제
