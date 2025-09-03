plugins {
  id("kairo-sample")
}

dependencies {
  api(project(":"))

  implementation(libs.kairo.coroutines)
  implementation(libs.kairo.dependencyInjection)
  api(libs.kairo.testing)
}
