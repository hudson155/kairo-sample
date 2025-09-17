plugins {
  kotlin("plugin.serialization")
  id("kairo-sample")
  id("com.google.devtools.ksp")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi")
  }
}

dependencies {
  ksp(enforcedPlatform(libs.kairo))
  implementation(enforcedPlatform(libs.kairo))

  ksp(libs.koin.ksp)

  implementation(libs.kairo.dependencyInjection)
  implementation(libs.kairo.exception)
  api(libs.kairo.feature)
  api(libs.kairo.id)
  implementation(libs.kairo.logging)
  implementation(libs.kairo.rest)
  implementation(libs.kairo.sql)
  implementation(libs.kairo.sql.postgres)
  implementation(libs.kairo.util)

  testImplementation(project(":testing"))

  testImplementation(libs.kairo.dependencyInjection.feature)
  testImplementation(libs.kairo.integrationTesting)
  testImplementation(libs.kairo.integrationTesting.postgres)
  testImplementation(libs.kairo.sql.feature)
}
