plugins {
  id("kairo-sample")
}

dependencies {
  api(project(":"))

  implementation(libs.exposed.jdbc)
  api(libs.kairo.dependencyInjection)
  api(libs.kairo.exceptionTesting)
  implementation(libs.kairo.sqlFeature)
  api(libs.kairo.testing)
  implementation(libs.testcontainers.postgres)
}
