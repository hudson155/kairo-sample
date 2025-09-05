plugins {
  kotlin("plugin.serialization")
  id("kairo-sample")
  id("kairo-sample-application")
  id("com.google.devtools.ksp")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi")
  }
}

dependencies {
  implementation(platform("io.arrow-kt:arrow-stack:2.1.2"))
  implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.10.2"))
  implementation(platform("org.jetbrains.exposed:exposed-bom:1.0.0-rc-1"))
  implementation(platform("io.ktor:ktor-bom:3.2.3"))
  implementation(platform("org.jetbrains.kotlinx:kotlinx-serialization-bom:1.9.0"))
  implementation(platform("org.slf4j:slf4j-bom:2.0.17"))

  implementation(kotlin("reflect"))

  implementation("io.github.oshai:kotlin-logging-jvm:7.0.13")
  implementation("org.slf4j:slf4j-api")
  implementation("org.slf4j:slf4j-simple")
  runtimeOnly("org.postgresql:r2dbc-postgresql:1.0.7.RELEASE")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
  implementation("org.jetbrains.exposed:exposed-core")
  implementation("org.jetbrains.exposed:exposed-crypt")
  implementation("org.jetbrains.exposed:exposed-json")
  implementation("org.jetbrains.exposed:exposed-kotlin-datetime")
  implementation("org.jetbrains.exposed:exposed-money")
  implementation("org.jetbrains.exposed:exposed-r2dbc")
  implementation("io.r2dbc:r2dbc-pool:1.0.2.RELEASE")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-core")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")

  implementation("io.ktor:ktor-serialization-kotlinx-json")
  implementation("io.ktor:ktor-server-core")
  implementation("io.ktor:ktor-server-auto-head-response")
  implementation("io.ktor:ktor-server-call-logging")
  implementation("io.ktor:ktor-server-content-negotiation")
  implementation("io.ktor:ktor-server-cors")
  implementation("io.ktor:ktor-server-default-headers")
  implementation("io.ktor:ktor-server-double-receive")
  implementation("io.ktor:ktor-server-forwarded-header")
  implementation("io.ktor:ktor-server-netty")
}

application {
  mainClass = "kairoSample.MainKt"
}
