plugins {
  kotlin("plugin.serialization")
  id("kairo-sample")
  id("com.google.devtools.ksp")
}

dependencies {
  ksp(enforcedPlatform(libs.kairo))
  implementation(enforcedPlatform(libs.kairo))

  ksp(libs.koin.ksp)

  implementation(libs.kairo.coroutines)
  implementation(libs.kairo.dependencyInjection)
  implementation(libs.kairo.exception)
  api(libs.kairo.feature)
  api(libs.kairo.id)
  implementation(libs.kairo.logging)
  implementation(libs.kairo.rest)
  implementation(libs.kairo.sql)
  implementation(libs.kairo.sqlPostgres)
  implementation(libs.koin.annotations)
  implementation(libs.koin.core)

  testImplementation(project(":testing"))

  testImplementation(libs.exposed.jdbc)
  testImplementation(libs.kairo.dependencyInjectionFeature)
  testImplementation(libs.kairo.featureTesting)
  testImplementation(libs.kairo.sqlFeature)
  testImplementation(libs.kairo.sqlFeaturePostgresTesting)
  testImplementation(libs.kairo.util)
  testRuntimeOnly(libs.postgres.jdbc)
}
