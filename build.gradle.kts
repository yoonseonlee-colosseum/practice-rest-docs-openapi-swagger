import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.gradle.api.tasks.Copy

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"

    id("org.asciidoctor.jvm.convert") version "4.0.2"
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

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("io.mockk:mockk:1.13.11")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.named<AsciidoctorTask>("asciidoctor") {
    dependsOn(tasks.named("test"))
    inputs.dir(snippetsDir)

    // Asciidoctor가 {snippets} 변수를 자동 인식하지 않으므로 직접 경로 지정
    attributes(
        mapOf("snippets" to file("build/generated-snippets").path)
    )

    baseDirFollowsSourceDir()
}

tasks.register<Copy>("copyRestDocsToStatic") {
    dependsOn(tasks.named("asciidoctor"))
    from("build/docs/asciidoc/")
    into("src/main/resources/static/docs")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
