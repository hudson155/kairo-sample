plugins {
  `kotlin-dsl`
}

repositories {
  gradlePluginPortal()
}

dependencies {
  // https://kotlinlang.org/docs/releases.html#release-details
  val kotlinVersion = "2.2.20"
  implementation(kotlin("gradle-plugin", kotlinVersion))
  implementation(kotlin("serialization", kotlinVersion))

  // https://plugins.gradle.org/plugin/com.google.cloud.artifactregistry.gradle-plugin
  val artifactRegistryVersion = "2.2.5"
  implementation("com.google.cloud.artifactregistry:artifactregistry-gradle-plugin:$artifactRegistryVersion")

  // https://github.com/google/ksp/releases
  val kspVersion = "2.2.20-2.0.3"
  implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:$kspVersion")

  // https://github.com/detekt/detekt/releases
  val detektVersion = "2.0.0-alpha.1"
  implementation("dev.detekt:detekt-gradle-plugin:$detektVersion")

  // https://github.com/GradleUp/shadow/releases
  val shadowVersion = "8.3.9"
  implementation("com.gradleup.shadow:shadow-gradle-plugin:$shadowVersion")
}

kotlin {
  compilerOptions {
    allWarningsAsErrors = true
  }
}
