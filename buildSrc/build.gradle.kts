plugins {
  `kotlin-dsl`
}

repositories {
  gradlePluginPortal()
}

dependencies {
  val kotlinVersion = "2.2.10" // https://kotlinlang.org/docs/releases.html#release-details
  implementation(kotlin("gradle-plugin", kotlinVersion))
  implementation(kotlin("serialization", kotlinVersion))

  // https://github.com/google/ksp/releases
  implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.10-2.0.2")

  // https://plugins.gradle.org/plugin/com.google.cloud.artifactregistry.gradle-plugin
  implementation("com.google.cloud.artifactregistry:artifactregistry-gradle-plugin:2.2.5")

  // https://github.com/GradleUp/shadow/releases
  implementation("com.gradleup.shadow:shadow-gradle-plugin:8.3.9")
}

kotlin {
  compilerOptions {
    allWarningsAsErrors = true
  }
}
