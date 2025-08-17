import com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer

plugins {
  java
  kotlin("jvm") version libs.versions.kotlin
  kotlin("plugin.serialization") version libs.versions.kotlin
  application
  id("com.google.cloud.artifactregistry.gradle-plugin") version libs.versions.googleArtifactRegistry
  id("io.gitlab.arturbosch.detekt") version libs.versions.detekt
  id("com.gradleup.shadow") version libs.versions.shadow
}

repositories {
  mavenLocal()
  mavenCentral()
  maven {
    url = uri("artifactregistry://us-central1-maven.pkg.dev/airborne-software/maven")
  }
}

private val javaVersion: JavaLanguageVersion = JavaLanguageVersion.of(21)

java {
  toolchain {
    languageVersion = javaVersion
  }
}

kotlin {
  jvmToolchain {
    languageVersion = javaVersion
  }
  compilerOptions {
    allWarningsAsErrors = true
    freeCompilerArgs.add("-Xannotation-default-target=param-property")
    freeCompilerArgs.add("-Xconsistent-data-class-copy-visibility")
    freeCompilerArgs.add("-Xcontext-parameters")
    freeCompilerArgs.add("-Xjsr305=strict")
    freeCompilerArgs.add("-Xlambdas=indy")
    freeCompilerArgs.add("-Xnested-type-aliases")
    freeCompilerArgs.add("-opt-in=kotlin.concurrent.atomics.ExperimentalAtomicApi")
    freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
    freeCompilerArgs.add("-opt-in=kotlin.uuid.ExperimentalUuidApi")
    freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi")
  }
}

dependencies {
  implementation(enforcedPlatform(libs.kairo))
  implementation(libs.kairo.application)
  implementation(libs.kairo.config)
  implementation(libs.kairo.healthCheck)
  implementation(libs.kairo.rest)
  implementation(libs.log4j.core)
  runtimeOnly(libs.log4j.slf4j)

  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${detekt.toolVersion}")
}

application {
  mainClass = "kairoSample.MainKt"
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
  testLogging {
    events("passed", "skipped", "failed")
  }
  useJUnitPlatform()
}

detekt {
  config.from(files("$rootDir/.detekt/config.yaml"))
  parallel = true
  autoCorrect = true
}

tasks.shadowJar {
  isZip64 = true
  archiveClassifier = "shadow"
  mergeServiceFiles()
  transform(Log4j2PluginsCacheFileTransformer())
}
