plugins {
  kotlin("plugin.serialization")
  id("kairo-sample")
  id("kairo-sample-application")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi") // Hocon.
  }
}

dependencies {
  api(enforcedPlatform(libs.kairo))

  implementation(project(":feature:library"))

  implementation(libs.kairo.application)
  implementation(libs.kairo.config)
  implementation(libs.kairo.dependencyInjection.feature)
  implementation(libs.kairo.healthCheck.feature)
  implementation(libs.kairo.rest.feature)
  api(libs.kairo.server) // Exposed for testing module.
  implementation(libs.kairo.sql.feature)
  implementation(libs.log4j.core)
  runtimeOnly(libs.log4j.json)
  runtimeOnly(libs.log4j.slf4j)
  runtimeOnly(libs.postgres.jdbc)
}

application {
  mainClass = "kairoSample.MainKt"
}
