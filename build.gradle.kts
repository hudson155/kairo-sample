plugins {
  kotlin("plugin.serialization")
  id("kairo-sample")
  id("kairo-sample-application")
  id("kairo-sample-ksp")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi")
  }
}

dependencies {
  implementation(libs.kairo.application)
  implementation(libs.kairo.config)
  implementation(libs.kairo.coroutines)
  implementation(libs.kairo.dependencyInjection.feature)
  implementation(libs.kairo.healthCheck.feature)
  implementation(libs.kairo.id)
  implementation(libs.kairo.logging)
  implementation(libs.kairo.rest.feature)
  implementation(libs.kairo.serialization)
  implementation(libs.kairo.sql.feature)
  implementation(libs.kairo.util)
  implementation(libs.log4j.core)
  runtimeOnly(libs.log4j.json)
  runtimeOnly(libs.log4j.slf4j)
}

application {
  mainClass = "kairoSample.MainKt"
}
