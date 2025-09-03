plugins {
  id("kairo-sample")
}

dependencies {
  implementation(enforcedPlatform(libs.kairo))

  api(project(":"))

  implementation(libs.kairo.coroutines)
  implementation(libs.kairo.dependencyInjectionFeature)
  api(libs.kairo.testing)
}
