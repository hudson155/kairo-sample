plugins {
  kotlin("plugin.serialization")
  id("kairo-sample")
  id("com.google.devtools.ksp")
}

dependencies {
  ksp(enforcedPlatform(libs.kairo))
  implementation(enforcedPlatform(libs.kairo))

  ksp(libs.koin.ksp)

  // implementation(libs.kairo.config)
  implementation(libs.kairo.coroutines)
  implementation(libs.kairo.dependencyInjection)
  api(libs.kairo.feature)
  // implementation(libs.kairo.healthCheckFeature)
  api(libs.kairo.id)
  implementation(libs.kairo.logging)
  implementation(libs.kairo.rest)
  implementation(libs.kairo.sql)
  // implementation(libs.kairo.util)
  implementation(libs.koin.annotations)
  implementation(libs.koin.core)
  // implementation(libs.log4j.core)
  // runtimeOnly(libs.log4j.json)
  // runtimeOnly(libs.log4j.slf4j)
  // runtimeOnly(libs.postgres.r2dbc)

  testImplementation(project(":testing"))

  testImplementation(libs.kairo.dependencyInjectionFeature)
  testImplementation(libs.kairo.idFeature)
  testImplementation(libs.kairo.sqlFeature)
}
