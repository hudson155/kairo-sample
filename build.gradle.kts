plugins {
  kotlin("plugin.serialization")
  id("kairo-sample")
  id("kairo-sample-application")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi")
  }
}

dependencies {
  api(enforcedPlatform(libs.kairo))

  implementation(project(":feature:library"))

  implementation(libs.kairo.application)
  implementation(libs.kairo.config)
  implementation(libs.kairo.dependencyInjectionFeature)
  implementation(libs.kairo.healthCheckFeature)
  implementation(libs.kairo.restFeature)
  api(libs.kairo.server)
  implementation(libs.kairo.sqlFeature)
  implementation(libs.log4j.core)
  runtimeOnly(libs.log4j.json)
  runtimeOnly(libs.log4j.slf4j)
  runtimeOnly(libs.postgres.r2dbc)
}

application {
  mainClass = "kairoSample.MainKt"
}
