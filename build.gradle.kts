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
  api(libs.kairo.application)
  api(libs.kairo.config)
  api(libs.kairo.coroutines)
  api(libs.kairo.datetime)
  api(libs.kairo.dependencyInjection.feature)
  api(libs.kairo.exception)
  api(libs.kairo.gcpSecretSupplier)
  api(libs.kairo.healthCheck.feature)
  api(libs.kairo.id)
  api(libs.kairo.logging)
  api(libs.kairo.optional)
  api(libs.kairo.protectedString)
  api(libs.kairo.reflect)
  api(libs.kairo.rest.feature)
  api(libs.kairo.serialization)
  api(libs.kairo.sql.feature)
  api(libs.kairo.sql.postgres)
  api(libs.kairo.util)
  api(libs.log4j)
  runtimeOnly(libs.log4j.json)
  runtimeOnly(libs.log4j.slf4j)
  runtimeOnly(libs.postgres.r2dbc)
  runtimeOnly(libs.postgres.gcp)

  testImplementation(libs.kairo.exception.testing)
  testImplementation(libs.kairo.integrationTesting)
  testImplementation(libs.kairo.integrationTesting.postgres)
  testImplementation(libs.kairo.testing)
}

application {
  mainClass = "kairoSample.MainKt"
}
