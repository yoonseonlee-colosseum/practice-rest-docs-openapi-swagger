# 📘 Practice REST Docs

이 프로젝트는 Spring REST Docs와 Swagger (OpenAPI 3) 를 결합하여 테스트 기반으로 API 명세서를 자동 생성 및 시각화하는 예제입니다.

1. 테스트 코드 실행 시 스니펫(snippet)이 생성되고,
2. 이를 openapi3 태스크가 OpenAPI 명세(openapi3.yaml)로 변환한 뒤 
3. Swagger UI를 통해 웹에서 확인할 수 있습니다.

---

## 🚀 실행 및 문서 확인 가이드

### 1️⃣ 프로젝트 빌드 
1️⃣ 프로젝트 빌드 및 문서 생성

API 명세 자동 생성을 위해 아래 명령어를 실행합니다.

```bash
./gradlew apiBuild
```

> 💡 apiBuild는 다음 작업을 순차적으로 수행합니다.
> 1. clean 
> 2. @Tag("restDocs") 가 지정된 테스트 케이스 수행 
> 3. 테스트 결과로 REST Docs 스니펫 생성 (build/generated-snippets/)
> 4. openapi3 태스크로 OpenAPI 명세(openapi3.yaml) 생성 
> 5. 최종 빌드(build/libs/application.jar)

#### 📁 생성 경로 예시:

```
build/
 ├── generated-snippets/       # 테스트 기반 API 스니펫
 │   ├── orders/
 │   │   ├── http-request.adoc
 │   │   ├── http-response.adoc
 │   │   ├── request-fields.adoc
 │   │   └── response-fields.adoc
 │   └── ...
 ├── api-spec/
 │   └── openapi3.yaml         # OpenAPI 명세서
 └── libs/
     └── application.jar        # Swagger UI 포함 실행 파일
```

### 2️⃣ 애플리케이션 실행

Swagger UI가 포함된 Spring Boot 애플리케이션을 실행합니다.

```bash
 java -jar build/libs/application.jar
```

### 3️⃣ 문서 확인

Spring Boot 애플리케이션을 실행한 뒤, 아래 URL로 접속하여 문서를 확인합니다.

👉 http://localhost:8080/swagger-ui/swagger-ui.html

> Swagger UI가 openapi3.yaml을 자동으로 불러와 REST Docs 기반 API 명세서를 시각화하여 보여줍니다.
> 각 API의 요청/응답 스펙, 필드 설명, 예시를 확인할 수 있습니다.


## ✅ 전체 빌드 플로우 요약

```
graph TD
  A[테스트 코드 실행] --> B[REST Docs 스니펫 생성 (build/generated-snippets)]
  B --> C[openapi3 변환 (build/api-spec/openapi3.yaml)]
  C --> D[Swagger UI 빌드 포함 (bootJar)]
  D --> E[브라우저에서 Swagger UI 확인 (/swagger-ui/swagger-ui.html)]
```
