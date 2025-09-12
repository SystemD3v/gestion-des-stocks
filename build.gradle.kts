plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "StockApplication"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")             // Base Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")    // ← Ajouté pour JPA et JpaRepository
    runtimeOnly("org.postgresql:postgresql")                                    // Pilote PostgreSQL
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
}
tasks.withType<Test> {
    useJUnitPlatform()
}
