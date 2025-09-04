plugins {
  id("kairo-sample")
}

dependencies {
  api(project(":"))

  api(libs.kairo.testing)
}
