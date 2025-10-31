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
  implementation(libs.kairo.datetime)
  implementation(libs.kairo.dependencyInjection.feature)
  implementation(libs.kairo.exception)
  implementation(libs.kairo.healthCheck.feature)
  implementation(libs.kairo.id)
  implementation(libs.kairo.logging)
  implementation(libs.kairo.optional)
  implementation(libs.kairo.protectedString)
  implementation(libs.kairo.reflect)
  implementation(libs.kairo.rest.feature)
  implementation(libs.kairo.serialization)
  implementation(libs.kairo.sql.feature)
  implementation(libs.kairo.sql.postgres)
  implementation(libs.kairo.util)
  implementation(libs.log4j.core)
  runtimeOnly(libs.log4j.json)
  runtimeOnly(libs.log4j.slf4j)

  testImplementation(libs.kairo.exception.testing)
  testImplementation(libs.kairo.integrationTesting)
  testImplementation(libs.kairo.integrationTesting.postgres)
  testImplementation(libs.kairo.testing)
}

application {
  mainClass = "kairoSample.MainKt"
}
