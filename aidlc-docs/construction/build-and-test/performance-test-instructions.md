# Performance Test Instructions

## MVP 성능 기준
- API 응답: 1초 이내
- SSE 이벤트 전달: 2초 이내
- 동시 접속: 10 테이블 이하

## 간이 성능 테스트 (MVP)

### API 응답 시간 확인
```bash
# 카테고리 조회
time curl -s http://localhost:8080/api/v1/categories | head -c 100

# 메뉴 조회
time curl -s http://localhost:8080/api/v1/menus?categoryId=1 | head -c 100

# 매장 조회
time curl -s http://localhost:8080/api/v1/stores | head -c 100
```

### SSE 연결 확인
```bash
# SSE 구독 (Ctrl+C로 종료)
curl -N http://localhost:8080/api/v1/events/store/1
```

### 예상 결과
- 모든 API 응답: 100ms 이내 (로컬 H2)
- SSE 연결: 즉시 수립
- MVP 성능 기준 충분히 충족
