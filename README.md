# pinpoint-quickstart-test-application

## Pinpoint Docker 기반 guide 에서 제공하는 Application 프로젝트 재구현

### 환경
- IntelliJ
- Java 8
- Maven
- Spring

### 이슈
- 특정 URL에 대해서 Mapping API 가 존재하지 않아 404 Not Found Page 로 인해서 Json 형태의 응답이 아닌 상황으로 에러 발생
- WAR 파일만 존재하여 디컴파일을 통해 재구현
- Java 6 버전의 지원 종료 및 Mac 환경에서 구성이 불가하여 Java 7, Java 8 을 통해 구현

### 구현
- Java 7 을 통해 재구성
- 컴파일은 Java 8 을 통해 빌드
  - 컴파일의 이슈는 IntelliJ IDE 디컴파일 시 Java 8 로 진행이 되면서 발생
