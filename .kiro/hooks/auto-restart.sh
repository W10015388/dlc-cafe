#!/bin/bash
PROJECT_ROOT="$(cd "$(dirname "$0")/../.." && pwd)"

# 최근 변경된 파일 확인 (최근 30초 이내)
BACKEND_CHANGED=$(find "$PROJECT_ROOT/backend/src" -name "*.java" -o -name "*.yml" -o -name "*.sql" -o -name "*.kts" 2>/dev/null | xargs stat -f "%m %N" 2>/dev/null | awk -v now="$(date +%s)" '$1 > now-30' | wc -l | tr -d ' ')
FRONTEND_CHANGED=$(find "$PROJECT_ROOT/frontend/src" -name "*.tsx" -o -name "*.ts" -o -name "*.css" -o -name "*.html" 2>/dev/null | xargs stat -f "%m %N" 2>/dev/null | awk -v now="$(date +%s)" '$1 > now-30' | wc -l | tr -d ' ')

# 백엔드 재시작 (Java/SQL/설정 변경 시)
if [ "$BACKEND_CHANGED" -gt 0 ]; then
  lsof -ti:8080 2>/dev/null | xargs kill -9 2>/dev/null
  sleep 1
  cd "$PROJECT_ROOT/backend" && nohup ./gradlew bootRun &>/tmp/backend.log &
  echo "백엔드 재시작됨 (변경 파일: $BACKEND_CHANGED개)" >&2
fi

# 프론트엔드 시작 (실행 중이 아닐 때만)
if ! lsof -ti:5173 >/dev/null 2>&1; then
  cd "$PROJECT_ROOT/frontend" && nohup npm run dev &>/tmp/frontend.log &
  echo "프론트엔드 시작됨" >&2
fi

exit 0
