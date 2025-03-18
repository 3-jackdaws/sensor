import io.swagger.codegen.v3.ClientOptInput
import io.swagger.codegen.v3.ClientOpts
import io.swagger.codegen.v3.CodegenConfigLoader
import io.swagger.codegen.v3.DefaultGenerator
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.parser.OpenAPIV3Parser
import io.swagger.v3.parser.core.models.ParseOptions

description = "api-monitor-sensors"

val buildDirectory: Directory = project.layout.buildDirectory.get()
val swaggerFile: String = "${projectDir}/src/main/resources/monitor-sensors-api.yaml"
val swaggerCodegenVersion: String by project
val swaggerVersion: String by project
val javaxVersion: String by project

plugins {
    java
    `java-library`
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.swagger.codegen.v3:swagger-codegen-maven-plugin:3.0.30")
    }
}

java {
    withSourcesJar()
    sourceSets {
        main {
            java {
                srcDirs(
                    "${project.buildDir}/classes/java/main",
                    "${project.buildDir}/src/main/java"
                )
            }
        }
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("io.swagger.codegen.v3:swagger-codegen:$swaggerCodegenVersion") {
        exclude("ch.qos.logback")
    }

    implementation("io.swagger.core.v3:swagger-annotations:$swaggerVersion")
    implementation("io.swagger.core.v3:swagger-models:$swaggerVersion")
    implementation("javax.annotation:javax.annotation-api:$javaxVersion")
}

tasks.named<Jar>("sourcesJar") {
    dependsOn("compileJava")
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

tasks {
    register<Copy>("copyApiDocumentation") {
        from("${project.projectDir}/src/main/resources")
        include("**/*.yaml")
        into("${buildDirectory}/resources/main/static")
    }

    register("generateApi") {
        dependsOn("copyApiDocumentation")
        doLast {
            val options = ParseOptions().apply {
                isResolve = true
            }

            val openAPI: OpenAPI = OpenAPIV3Parser().read(swaggerFile, null, options)
            val clientOpts = ClientOptInput().apply {
                openAPI(openAPI)
                config = CodegenConfigLoader.forName("spring").apply {
                    outputDir = buildDirectory.asFile.absolutePath
                    typeMapping().apply {
                        put("Date", "LocalDate")
                        put("DateTime", "OffsetDateTime")
                    }
                    importMapping().apply {
                        put("Date", "java.time.LocalDate")
                        put("DateTime", "java.time.OffsetDateTime")
                    }
                }

                opts = ClientOpts().apply {
                    properties = mapOf(
                        "useBeanValidation" to "true",
                        "dateLibrary" to "java11",
                        "useTags" to "true",
                        "useSetterAnnotations" to "false",
                        "useFieldAnnotations" to "true",
                        "defaultInterfaces" to "false",
                        "interfaceOnly" to "true",
                        "apiPackage" to "com.mart.api",
                        "modelPackage" to "com.mart.dto"
                    )
                }
            }
            DefaultGenerator().opts(clientOpts).generate()
        }
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}
tasks.named<Jar>("jar") {
    enabled = true
}