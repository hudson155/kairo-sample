import org.gradle.jvm.tasks.Jar

plugins {
  java
  kotlin("jvm")
  id("com.google.cloud.artifactregistry.gradle-plugin")
  id("io.gitlab.arturbosch.detekt")
}

repositories {
  mavenLocal()
  mavenCentral()
  maven {
    url = uri("artifactregistry://us-central1-maven.pkg.dev/kairo-13/kairo-13")
  }
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

kotlin {
  jvmToolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
  explicitApi()
  compilerOptions {
    allWarningsAsErrors = true
    freeCompilerArgs.add("-Xconsistent-data-class-copy-visibility")
    freeCompilerArgs.add("-opt-in=kotlin.uuid.ExperimentalUuidApi")
  }
}

dependencies {
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${detekt.toolVersion}")
}

/**
 * Detekt makes the [check] task depend on the [detekt] task automatically.
 * However, since the [detekt] task doesn't support type resolution
 * (at least, not until the next major version of Detekt),
 * some issues get missed.
 *
 * Here, we remove the default dependency and replace it with [detektMain] and [detektTest]
 * which do support type resolution.
 *
 * This can be removed once the next major version of Detekt is released.
 */
tasks.named("check").configure {
  setDependsOn(dependsOn.filterNot { it is TaskProvider<*> && it.name == "detekt" })
  dependsOn("detektMain", "detektTest")
}

tasks.test {
  environment("KAIRO_ALLOW_INSECURE_CONFIG_SOURCES", "true")
  environment("KAIRO_CLEAN_DATABASE", "true")
  environment("LOG_LEVEL", "debug")
  testLogging {
    events("passed", "skipped", "failed")
  }
  useJUnitPlatform()
  ignoreFailures = project.hasProperty("ignoreTestFailures") // This property may be set during CI.
}

detekt {
  config.from(files("$rootDir/.detekt/config.yaml"))
  parallel = true
}

/**
 * By default, JAR archives are named according to the Gradle module name.
 * This causes collisions when multiple Gradle modules have the same name,
 * which can happen with nested multimodule projects.
 *
 * This plugin changes the archive name to be the fully-qualified project path instead,
 * which helps avoid these colisions.
 */
tasks.withType<Jar> {
  archiveBaseName = project.path.drop(1).replace(':', '-')
}
