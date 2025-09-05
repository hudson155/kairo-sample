plugins {
  java
  kotlin("jvm") version "2.2.10"
  application
}

repositories {
  mavenLocal()
  mavenCentral()
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

kotlin {
  jvmToolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
  compilerOptions {
    freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
  }
}

dependencies {
  implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.10.2"))
  implementation(platform("org.jetbrains.exposed:exposed-bom:1.0.0-rc-1"))
  implementation(platform("io.ktor:ktor-bom:3.2.3"))
  implementation(platform("org.slf4j:slf4j-bom:2.0.17"))

  implementation("org.slf4j:slf4j-api")
  runtimeOnly("org.slf4j:slf4j-simple")

  implementation("org.jetbrains.exposed:exposed-core")
  implementation("org.jetbrains.exposed:exposed-kotlin-datetime")
  implementation("org.jetbrains.exposed:exposed-r2dbc")
  runtimeOnly("org.postgresql:r2dbc-postgresql:1.0.7.RELEASE")

  implementation("io.ktor:ktor-server-core")
  implementation("io.ktor:ktor-server-call-logging")
  implementation("io.ktor:ktor-server-content-negotiation")
  implementation("io.ktor:ktor-server-netty")
}

application {
  mainClass = "kairoSample.MainKt"
}
