import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"

    // 문서화 관련
    id("org.asciidoctor.jvm.convert") version "4.0.2"
    id("com.epages.restdocs-api-spec") version "0.19.2"
}

group = "com.practice"
version = "0.0.1-SNAPSHOT"
description = "practice-rest-docs"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

val snippetsDir by extra { file("build/generated-snippets") }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("io.mockk:mockk:1.13.11")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.19.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

/** REST Docs 테스트 태스크 */
tasks.register<Test>("restDocsTest") {
    outputs.dir(snippetsDir)

    useJUnitPlatform {
        includeTags("restDocs") // @Tag("restDocs") 테스트만 수행
    }

    // 테스트 후 문서화 태스크 자동 실행
    finalizedBy("asciidoctor")
    finalizedBy("openapi3")
}

/** Asciidoctor 설정 */
tasks.named<AsciidoctorTask>("asciidoctor") {
    dependsOn(tasks.named("restDocsTest"))
    inputs.dir(snippetsDir)

    // Asciidoctor가 {snippets} 변수를 자동 인식하지 않으므로 직접 경로 지정
    attributes(mapOf("snippets" to file("build/generated-snippets").path))

    baseDirFollowsSourceDir()
}

/** Spring Boot Jar 빌드 설정 */
tasks.named<BootJar>("bootJar") {
    dependsOn("asciidoctor", "openapi3")

    // REST Docs HTML 포함
    from("build/docs/asciidoc/") {
        into("BOOT-INF/classes/static/docs")
    }

    // OpenAPI YAML 포함
    from("build/api-spec/") {
        include("openapi3.yaml")
        into("BOOT-INF/classes/static/swagger")
    }

    // Swagger UI 정적 리소스 포함 (프로젝트 루트에 swagger-ui 폴더 있을 경우)
    from("swagger-ui/") {
        into("BOOT-INF/classes/static/swagger")
    }

    archiveFileName.set("application.jar")
}

/** API 문서 빌드 파이프라인 */
tasks.register<GradleBuild>("apiBuild") {
    tasks = listOf("clean", "restDocsTest", "build")
}

openapi3 {
    setServer("http://localhost:8080")
    title = "spring-rest-docs-guide"
    description = "Spring REST Docs 테스트 생성물 생성시 추가생성되는 OpenAPI 문서이용"
    version = "${project.version}"
    format = "yaml"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
