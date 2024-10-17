plugins {
  `kotlin-dsl`
}

repositories {
  gradlePluginPortal()
}

dependencies {
  // https://kotlinlang.org/docs/releases.html#release-details
  implementation(kotlin("gradle-plugin", "2.0.21"))

  // https://plugins.gradle.org/plugin/com.google.cloud.artifactregistry.gradle-plugin
  implementation("com.google.cloud.artifactregistry", "artifactregistry-gradle-plugin", "2.2.3")

  // https://plugins.gradle.org/plugin/io.gitlab.arturbosch.detekt
  implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.7")

  // https://plugins.gradle.org/plugin/com.gradleup.shadow
  implementation("com.gradleup.shadow:shadow-gradle-plugin:8.3.3")
}

kotlin {
  explicitApi()
  compilerOptions {
    allWarningsAsErrors = true
  }
}