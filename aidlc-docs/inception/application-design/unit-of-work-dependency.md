# Unit of Work Dependencies

## Dependency Matrix

| Unit | Backend | Frontend |
|------|---------|----------|
| Backend | - | |
| Frontend | X (REST API, SSE) | - |

X = 의존함

## Integration Points

- **Frontend → Backend**: REST API 호출 (HTTP), SSE 이벤트 수신
- **Backend → Frontend**: 없음 (Backend는 Frontend에 의존하지 않음)

## 병렬 개발 전략

두 Unit은 병렬로 개발 가능:
- Backend: API 엔드포인트 구현 + 테스트
- Frontend: Mock 데이터로 UI 개발 → Backend 완성 후 실제 API 연동
- 통합: Build and Test 단계에서 통합 테스트 수행
