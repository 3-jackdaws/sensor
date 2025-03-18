val mapstructVersion: String by project
val springCloudVersion: String by project
val jetbrainsAnnotationsVersion: String by project
val hibernateTypesVersion: String by project

description = "service-monitor-sensors"

plugins {
    java
    `java-library`
    groovy
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "1.9.0"
}

tasks {
    bootRun {
        enabled = true
        mainClass.set("com.mart.monitorsensors.MonitorSensorsApplication")
    }
    bootJar {
        archiveFileName.set(project.name + ".jar")
        mainClass.set("com.mart.monitorsensors.MonitorSensorsApplication")
    }
}

springBoot {
    buildInfo()
}

dependencies {
    implementation(project(":api-monitor-sensors"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.flywaydb:flyway-database-postgresql:10.20.1")

    implementation("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")

    implementation("org.springframework.boot:spring-boot-starter-validation")


    implementation("org.postgresql:postgresql:42.6.0")

    //mapstruct
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    compileOnly("org.mapstruct:mapstruct-processor:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")


    //tests
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
}