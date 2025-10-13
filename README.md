# 📘 Practice REST Docs

이 프로젝트는 **Spring REST Docs** 기반으로 API 명세서를 자동 생성하는 예제입니다.  
테스트 코드를 실행하면 스니펫(snippets)이 생성되고,  
Asciidoctor를 통해 정적 HTML 문서로 변환됩니다.

---

## 🚀 실행 및 문서 확인 가이드

### 1️⃣ 프로젝트 빌드
테스트 및 REST Docs 생성을 위해 전체 프로젝트를 빌드합니다.

```bash
./gradlew clean build
```

### 2️⃣ 테스트 실행

테스트 실행 시, build/generated-snippets/ 디렉터리에 API 문서 스니펫이 생성됩니다.

```bash
./gradlew clean test
```

#### 📁 생성 경로 예시:

```
build/generated-snippets/
 ├── products/
 │   ├── http-request.adoc
 │   ├── http-response.adoc
 │   ├── request-fields.adoc
 │   └── response-fields.adoc
 └── hello/
     ├── http-request.adoc
     ├── http-response.adoc
     └── response-fields.adoc
```

### 3️⃣ 스니펫 기반 Asciidoctor 변환

테스트 결과로 생성된 스니펫을 기반으로 .adoc 파일을 HTML로 변환합니다.

```bash
./gradlew asciidoctor
```

#### 🧾 출력 결과:

```bash
build/docs/asciidoc/index.html
```

이 파일이 문서의 정적 HTML 원본입니다.

### 4️⃣ 정적 파일 생성

생성된 HTML 문서를 Spring Boot의 정적 리소스 디렉터리(src/main/resources/static/docs)로 복사합니다.

```bash
./gradlew copyRestDocsToStatic
```

#### 📁 복사 결과 예시:

```
src/main/resources/static/docs/
 ├── index.html
 ├── products/
 │   ├── product-create.html
 │   ├── product-get.html
 │   └── product-update.html
 └── common/
     └── error.html
```

> 💡 이렇게 복사된 파일들은 Spring Boot 정적 리소스로 서빙되며,   
> 배포 후 /docs/index.html 경로를 통해 접근할 수 있습니다.

### 5️⃣ 문서 확인

Spring Boot 애플리케이션을 실행한 뒤, 아래 URL로 접속하여 문서를 확인합니다.

👉 http://localhost:8080/docs/index.html

> 🧭 index.html은 도메인별 문서(products 등)를 모두 포함한 메인 문서입니다.
> 각 도메인별 .adoc 파일이 포함된 구조라면,   
> 문서 내 목차(좌측 TOC)를 통해 세부 API로 바로 이동할 수 있습니다.


## ✅ 전체 빌드 플로우 요약

```
graph TD
  A[테스트 코드 실행] --> B[스니펫 생성 (build/generated-snippets)]
  B --> C[Asciidoctor 변환 (build/docs/asciidoc)]
  C --> D[정적 파일 복사 (src/main/resources/static/docs)]
  D --> E[브라우저에서 /docs/index.html 확인]
```
