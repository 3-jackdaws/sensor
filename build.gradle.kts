val springBootVersion: String by project
val springCloudVersion: String by project
val springdocOpenapiUiVersion: String by project
val lombokVersion: String by project
val openTelemetryVersion: String by project
val gradleVersion: String by project
val log4jVersion: String by project

plugins {
	java
	idea
	`maven-publish`
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
}

allprojects {
	repositories {
		mavenLocal()
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "project-report")
	apply(plugin = "maven-publish")
	apply(plugin = "io.spring.dependency-management")

	dependencyManagement {
		imports {
			mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
			mavenBom("io.opentelemetry:opentelemetry-bom:$openTelemetryVersion")
		}
	}
	dependencies {
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

		compileOnly("org.projectlombok:lombok:$lombokVersion")
		annotationProcessor("org.projectlombok:lombok:$lombokVersion")
	}

	configure<JavaPluginExtension> {
		withSourcesJar()
		withJavadocJar()
	}

	publishing {
		repositories {
			mavenLocal()
		}
	}
}

tasks {
	wrapper {
		gradleVersion = "${gradleVersion}"
		distributionType = Wrapper.DistributionType.ALL
	}
	bootJar {
		enabled = false
	}
	jar {
		enabled = true
	}
}

tasks.register<DefaultTask>("publishLogsVersion") {
	description = "Logs the version for us to see."
	doLast {
		logger.lifecycle("Published with Version: $version")
	}
}

tasks.publishToMavenLocal {
	finalizedBy("publishLogsVersion")
}