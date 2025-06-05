import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  val kotlinVersion = "2.1.0"
  kotlin("jvm") version kotlinVersion
  kotlin("plugin.spring") version kotlinVersion
  kotlin("plugin.jpa") version kotlinVersion
  id("org.springframework.boot") version "3.5.0"        // :contentReference[oaicite:0]{index=0}
  id("io.spring.dependency-management") version "1.1.7" // :contentReference[oaicite:1]{index=1}
  id("me.qoomon.git-versioning") version "6.4.4"         // :contentReference[oaicite:2]{index=2}
  id("com.diffplug.spotless") version "7.0.4"           // :contentReference[oaicite:3]{index=3}
  id("jacoco")
}

group = "com.stefanbratanov"

version = "develop"

gitVersioning.apply {
  refs {
    tag("v(?<version>.*)") { version = "\${ref.version}" }
    rev { version = "develop" }
  }
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

jacoco { toolVersion = "0.8.14" }                      // :contentReference[oaicite:4]{index=4}

repositories { mavenCentral() }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.data:spring-data-keyvalue")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-cache")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.postgresql:postgresql:42.7.6")                        // :contentReference[oaicite:7]{index=7}
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.8") // :contentReference[oaicite:8]{index=8}
  implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")
  implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")   // :contentReference[oaicite:9]{index=9}
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("org.apache.commons:commons-lang3:3.17.0")                // :contentReference[oaicite:10]{index=10}
  implementation("org.apache.commons:commons-math3:3.6.1")                // :contentReference[oaicite:11]{index=11}
  implementation("org.jsoup:jsoup:1.20.1")                                // :contentReference[oaicite:12]{index=12}
  implementation("org.apache.pdfbox:pdfbox:3.0.5")                         // :contentReference[oaicite:13]{index=13}
  implementation("me.xdrop:fuzzywuzzy:1.4.0")                              // :contentReference[oaicite:14]{index=14}
  implementation("com.cloudinary:cloudinary-http45:1.39.0")                // :contentReference[oaicite:15]{index=15}
  implementation("org.seleniumhq.selenium:selenium-java:4.9.0")
  implementation("com.codeborne:phantomjsdriver:1.5.0")                    // :contentReference[oaicite:16]{index=16}
  implementation("io.github.bonigarcia:webdrivermanager:6.1.0")             // :contentReference[oaicite:17]{index=17}
  testImplementation("org.junit.jupiter:junit-jupiter:5.14.0")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.28.1")       // :contentReference[oaicite:18]{index=18}
  testImplementation("io.mockk:mockk:1.14.2")                              // :contentReference[oaicite:19]{index=19}
  testImplementation("com.ninja-squad:springmockk:4.0.2")                  // :contentReference[oaicite:20]{index=20}
  testImplementation("org.skyscreamer:jsonassert:1.5.3")                    // :contentReference[oaicite:21]{index=21}
  testImplementation("org.mock-server:mockserver-spring-test-listener:5.15.0") // :contentReference[oaicite:22]{index=22}
  implementation("com.h2database:h2")
}

springBoot { buildInfo() }

tasks.getByName<Jar>("jar") { enabled = false }

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all-compatibility")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging { showStandardStreams = true }
}

configure<SpotlessExtension> { kotlin { ktfmt("0.53").googleStyle() } }

tasks.jacocoTestReport { reports { xml.required.set(true) } }
