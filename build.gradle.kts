plugins {
  kotlin("plugin.serialization")
  id("kairo-sample")
  id("kairo-sample-application")
  // id("com.google.devtools.ksp")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi")
  }
}

dependencies {
  // ksp(enforcedPlatform(libs.kairo))
  implementation(enforcedPlatform(libs.kairo))

  implementation(project(":feature:library"))

  // ksp(libs.koin.ksp)

  implementation(libs.kairo.application)
  implementation(libs.kairo.config)
  // implementation(libs.kairo.coroutines)
  implementation(libs.kairo.dependencyInjectionFeature)
  implementation(libs.kairo.healthCheckFeature)
  implementation(libs.kairo.idFeature)
  // implementation(libs.kairo.logging)
  implementation(libs.kairo.restFeature)
  api(libs.kairo.server)
  implementation(libs.kairo.sqlFeature)
  // implementation(libs.kairo.util)
  // implementation(libs.koin.annotations)
  // implementation(libs.koin.core)
  implementation(libs.log4j.core)
  runtimeOnly(libs.log4j.json)
  runtimeOnly(libs.log4j.slf4j)
  runtimeOnly(libs.postgres.r2dbc)
}

application {
  mainClass = "kairoSample.MainKt"
}
