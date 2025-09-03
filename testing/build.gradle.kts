plugins {
  id("kairo-sample")
}

dependencies {
  api(project(":"))

  implementation(libs.kairo.coroutines)
  implementation(libs.kairo.dependencyInjectionFeature)
  api(libs.kairo.testing)
}
