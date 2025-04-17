plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "EntrenaSync"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testRuntimeOnly("com.h2database:h2")
    // cache
    implementation("org.springframework.boot:spring-boot-starter-cache")

    //jpa database (PostgreSQL)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Logback por defecto
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.slf4j:slf4j-api:2.0.9")

    //mapeo de columnas de hstore(funcionalidad de postgres) a map
    // https://mvnrepository.com/artifact/com.vladmihalcea/hibernate-types-60
    implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.9.5")
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("org.hibernate.orm:hibernate-core:6.6.0.Final")

    //web
    implementation("org.springframework.boot:spring-boot-starter-web")

    //validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // serialization
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
