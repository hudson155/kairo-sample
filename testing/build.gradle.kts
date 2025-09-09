plugins {
  id("kairo-sample")
}

dependencies {
  api(project(":"))

  api(libs.kairo.exception.testing)
  api(libs.kairo.testing)
}
