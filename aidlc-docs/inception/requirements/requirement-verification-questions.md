# Requirements Verification Questions

아래 질문들에 답변해 주세요. 각 질문의 [Answer]: 태그 뒤에 선택지 문자를 입력해 주세요.
제공된 선택지가 맞지 않으면 마지막 옵션(Other)을 선택하고 설명을 추가해 주세요.

---

## Question 1
프로젝트의 기술 스택으로 어떤 것을 사용하시겠습니까? (Backend)

A) Java + Spring Boot
B) Node.js + Express/NestJS
C) Python + FastAPI/Django
D) Go
E) Other (please describe after [Answer]: tag below)

[Answer]: A

## Question 2
프로젝트의 기술 스택으로 어떤 것을 사용하시겠습니까? (Frontend)

A) React
B) Vue.js
C) Next.js
D) Angular
E) Other (please describe after [Answer]: tag below)

[Answer]: A

## Question 3
데이터베이스로 어떤 것을 사용하시겠습니까?

A) PostgreSQL
B) MySQL
C) DynamoDB (NoSQL)
D) MongoDB (NoSQL)
E) Other (please describe after [Answer]: tag below)

[Answer]: A

## Question 4
배포 환경은 어디를 대상으로 하시겠습니까?

A) AWS (ECS, Lambda 등 AWS 서비스 활용)
B) 로컬/온프레미스 (Docker Compose 등)
C) 기타 클라우드 (GCP, Azure)
D) 배포 환경은 나중에 결정 (로컬 개발 환경 우선)
E) Other (please describe after [Answer]: tag below)

[Answer]: D

## Question 5
동시 접속 매장 수 및 트래픽 규모를 어떻게 예상하시나요?

A) 소규모 (1~10개 매장, 동시 주문 50건 이하)
B) 중규모 (10~100개 매장, 동시 주문 500건 이하)
C) 대규모 (100개+ 매장, 동시 주문 1000건 이상)
D) MVP 단계에서는 규모 고려 불필요 (단일 매장 테스트)
E) Other (please describe after [Answer]: tag below)

[Answer]: D

## Question 6
관리자 계정 관리 방식은 어떻게 하시겠습니까?

A) 매장당 1개의 관리자 계정 (매장 식별자 + 비밀번호)
B) 매장당 다수의 관리자 계정 (역할별 권한 분리)
C) 슈퍼 관리자 + 매장 관리자 계층 구조
D) Other (please describe after [Answer]: tag below)

[Answer]: C

## Question 7
메뉴 이미지 관리 방식은 어떻게 하시겠습니까?

A) 외부 이미지 URL 직접 입력 (별도 이미지 저장소 없음)
B) 서버에 이미지 파일 업로드 및 저장
C) 클라우드 스토리지 (S3 등) 활용
D) Other (please describe after [Answer]: tag below)

[Answer]: D (로컬 파일 시스템에 이미지 저장)

## Question 8
메뉴 관리 기능은 MVP에 포함하시겠습니까? (요구사항 문서에 메뉴 관리가 정의되어 있으나 MVP 범위에는 명시되지 않았습니다)

A) Yes - MVP에 메뉴 관리(CRUD) 포함
B) No - MVP에서는 시드 데이터로 메뉴 제공, 메뉴 관리는 이후 구현
C) Other (please describe after [Answer]: tag below)

[Answer]: A

## Question 9
테이블 태블릿의 자동 로그인에서 "테이블 비밀번호"는 어떤 용도인가요?

A) 매장 관리자가 태블릿 초기 설정 시 사용하는 인증 수단 (고객은 비밀번호 입력 불필요)
B) 고객이 테이블에 앉을 때 입력하는 인증 수단
C) Other (please describe after [Answer]: tag below)

[Answer]: A

## Question 10
주문 상태 실시간 업데이트(고객 화면)는 MVP에 포함하시겠습니까? (요구사항에 "선택사항"으로 표기됨)

A) Yes - SSE 또는 polling으로 고객 화면에서도 주문 상태 실시간 반영
B) No - 고객 화면에서는 수동 새로고침으로 주문 상태 확인
C) Other (please describe after [Answer]: tag below)

[Answer]: A

## Question 11: Security Extensions
이 프로젝트에 보안 extension 규칙을 적용하시겠습니까?

A) Yes — 모든 SECURITY 규칙을 blocking constraint로 적용 (프로덕션 수준 애플리케이션에 권장)
B) No — 모든 SECURITY 규칙 건너뛰기 (PoC, 프로토타입, 실험적 프로젝트에 적합)
C) Other (please describe after [Answer]: tag below)

[Answer]: B
