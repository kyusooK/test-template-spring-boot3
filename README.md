# Spring Boot 3.x DDD 템플릿

이 템플릿은 도메인 주도 설계(DDD) 원칙과 클린 아키텍처를 따르는 Spring Boot 3.x 애플리케이션을 개발하기 위한 기반을 제공합니다.

## 주요 기능

- **Spring Boot 3.x**: Java 17 기반의 최신 Spring Boot 3.x 플랫폼
- **클린 아키텍처**: 도메인, 애플리케이션, 인프라스트럭처 레이어로 구조화
- **DDD 어노테이션**: DDD 특화 어노테이션(@AggregateRoot, @Entity, @ValueObject, @Repository, @DomainEvent, @EventListener 등)
- **이벤트 기반 아키텍처**: 이벤트 발행 및 소비를 위한 Kafka 메시징 통합
- **RESTful API**: RESTful 컨트롤러 예제 포함
- **OpenFeign 클라이언트**: 서비스 간 통신을 위한 설정
- **유효성 검사**: Spring Validation을 사용한 내장 유효성 검사
- **테스트**: BDD 스타일 테스트를 위한 Cucumber 포함

## 프로젝트 구조

템플릿은 클린 아키텍처 원칙을 따르며 다음과 같은 레이어로 구성됩니다:

```
src/main/java/{{options.package}}/
  ├── domain/               # 도메인 레이어 - 비즈니스 로직 및 규칙
  │   ├── {{entity}}/       # 애그리게이트 루트, 엔티티, 값 객체
  │   ├── event/            # 도메인 이벤트
  │   └── annotation/       # DDD 어노테이션
  ├── application/          # 애플리케이션 레이어 - 도메인 객체 조정
  │   ├── service/          # 애플리케이션 서비스
  │   ├── dto/              # 데이터 전송 객체
  │   └── port/             # 인프라스트럭처 레이어를 위한 포트
  └── infrastructure/       # 인프라스트럭처 레이어 - 기술 세부 사항
      ├── rest/             # REST 컨트롤러
      ├── kafka/            # Kafka 구성 및 컴포넌트
      ├── persistence/      # 리포지토리 구현
      └── feign/            # Feign 클라이언트 구현
```

## 시작하기

### 전제 조건

- Java 17 이상
- Maven 3.8.x 이상
- Kafka (메시징용)

### 템플릿 사용하기

이 템플릿은 MSA-EZ 플랫폼에서 사용됩니다. 사용 방법:

1. MSA-EZ에서 템플릿 선택
2. 도메인 모델 구성
3. 코드 생성
4. IDE에서 생성된 코드 임포트

### 수동 구성

수동으로 애플리케이션을 구성하는 경우:

1. `application.yml`에서 데이터베이스 및 Kafka 구성 업데이트
2. 패키지 이름 및 기타 속성 구성
3. 도메인 모델 및 비즈니스 로직 구현

## DDD 어노테이션

템플릿에는 다음과 같은 DDD 특화 어노테이션이 포함되어 있습니다:

- `@AggregateRoot`: DDD의 애그리게이트 루트를 표시
- `@Entity`: DDD의 엔티티를 표시
- `@ValueObject`: DDD의 값 객체를 표시
- `@Repository`: DDD의 리포지토리를 표시
- `@DomainEvent`: 도메인 이벤트를 표시
- `@EventListener`: 이벤트 핸들러를 표시

## 이벤트 기반 아키텍처

템플릿은 다음을 통해 이벤트 기반 아키텍처를 지원합니다:

1. 애그리게이트에서 발행하는 도메인 이벤트
2. 이벤트를 구독하는 이벤트 핸들러
3. 안정적인 메시징을 위한 Kafka 통합

이벤트 발행 예제:

```java
// 애그리게이트 메서드 내에서
MyEntityCreatedEvent event = new MyEntityCreatedEvent(this);
event.publishAfterCommit();
```

이벤트 처리 예제:

```java
@Component
public class MyEventHandler {
    @EventListener(eventType = "MyEntityCreatedEvent")
    public void handle(MyEntityCreatedEvent event) {
        // 이벤트 처리
    }
}
```

## 테스트

템플릿에는 Cucumber를 사용한 BDD 스타일 테스트 지원이 포함되어 있습니다:

1. `src/test/resources/features`에 있는 기능 파일
2. `src/test/java/{{options.package}}/cucumber`에 있는 스텝 정의
3. Spring Boot Test와의 통합

## 기여하기

기여를 환영합니다! 풀 리퀘스트를 제출해 주세요.

## Running in local development environment

```
mvn spring-boot:run
```

## Packaging and Running in docker environment

```
mvn package -B -DskipTests
docker build -t username/{{name}}:v1 .
docker run username/{{name}}:v1
```

## Push images and running in Kubernetes

```
docker login 
# in case of docker hub, enter your username and password

docker push username/{{name}}:v1
```

Edit the deployment.yaml under the /kubernetes directory:
```
    spec:
      containers:
        - name: {{name}}
          image: username/{{name}}:latest   # change this image name
          ports:
            - containerPort: 8080

```

Apply the yaml to the Kubernetes:
```
kubectl apply -f kubernetes/deployment.yaml
```

See the pod status:
```
kubectl get pods -l app={{name}}
```

If you have no problem, you can connect to the service by opening a proxy between your local and the kubernetes by using this command:
```
# new terminal
kubectl port-forward deploy/{{name}} 8080:8080

# another terminal
http localhost:8080
```

If you have any problem on running the pod, you can find the reason by hitting this:
```
kubectl logs -l app={{name}}
```

Following problems may be occurred:

1. ImgPullBackOff:  Kubernetes failed to pull the image with the image name you've specified at the deployment.yaml. Please check your image name and ensure you have pushed the image properly.
1. CrashLoopBackOff: The spring application is not running properly. If you didn't provide the kafka installation on the kubernetes, the application may crash. Please install kafka firstly:

https://labs.msaez.io/#/courses/cna-full/full-course-cna/ops-utility

