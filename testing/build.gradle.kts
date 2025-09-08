plugins {
  id("kairo-sample")
}

dependencies {
  api(project(":"))

  api(libs.kairo.exceptionTesting)
  api(libs.kairo.testing)
}
