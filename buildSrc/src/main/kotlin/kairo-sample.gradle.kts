import io.gitlab.arturbosch.detekt.Detekt

plugins {
  java
  kotlin("jvm")
  id("com.google.cloud.artifactregistry.gradle-plugin")
  id("io.gitlab.arturbosch.detekt")
}

repositories {
  mavenLocal()
  mavenCentral()
  artifactRegistry()
}

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

tasks.withType<Detekt> {
  exclude("org/koin/ksp/generated")
}
