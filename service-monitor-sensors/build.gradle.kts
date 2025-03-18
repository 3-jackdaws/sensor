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
    id("org.flywaydb.flyway")
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

    //springboot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    //security
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")

    implementation("org.springframework.boot:spring-boot-starter-validation")


    implementation("org.postgresql:postgresql")

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