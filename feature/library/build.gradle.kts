plugins {
  kotlin("plugin.serialization")
  id("com.google.devtools.ksp")
  id("kairo-sample")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi") // EncodeDefault.
  }
}

dependencies {
  ksp(platform(libs.kairo))
  implementation(platform(libs.kairo))

  ksp(libs.koin.ksp)

  implementation(libs.guava)
  implementation(libs.kairo.dependencyInjection)
  implementation(libs.kairo.exception)
  api(libs.kairo.feature)
  implementation(libs.kairo.id)
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

ksp {
  arg("KOIN_CONFIG_CHECK", "true")
}
