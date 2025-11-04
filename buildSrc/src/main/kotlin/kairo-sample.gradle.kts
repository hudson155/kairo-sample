import dev.detekt.gradle.Detekt
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
  java
  kotlin("jvm")
  id("com.google.cloud.artifactregistry.gradle-plugin")
  id("dev.detekt")
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
  explicitApi()
  compilerOptions {
    allWarningsAsErrors = true
    freeCompilerArgs.add("-Xannotation-default-target=param-property")
    freeCompilerArgs.add("-Xconsistent-data-class-copy-visibility")
    freeCompilerArgs.add("-Xcontext-parameters")
    freeCompilerArgs.add("-Xjsr305=strict")
    freeCompilerArgs.add("-Xlambdas=indy")
    freeCompilerArgs.add("-opt-in=kotlin.concurrent.atomics.ExperimentalAtomicApi")
    freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
    freeCompilerArgs.add("-opt-in=kotlin.uuid.ExperimentalUuidApi")
  }
}

dependencies {
  implementation(platform(Airborne.kairo))
  /**
   * TODO: Choosing a specific Netty version
   *  is a workaround to deal with the fact that kairo-sql doesn't work properly with Netty 4.2,
   *  but kairo-rest expects Netty 4.2.
   */
  implementation(enforcedPlatform("io.netty:netty-bom:4.1.128.Final"))
  detektPlugins("dev.detekt:detekt-rules-ktlint-wrapper:${detekt.toolVersion.get()}")
}

/**
 * Detekt makes the [check] task depend on the [detekt] task automatically.
 * However, since the [detekt] task doesn't support type resolution,
 * some issues get missed.
 *
 * Here, we remove the default dependency and replace it with [detektMain] and [detektTest]
 * which do support type resolution.
 */
tasks.named("check").configure {
  setDependsOn(dependsOn.filterNot { it is TaskProvider<*> && it.name == "detekt" })
  dependsOn("detektMain", "detektTest")
}

tasks.test {
  testLogging {
    exceptionFormat = TestExceptionFormat.FULL
    events("passed", "skipped", "failed")
  }
  useJUnitPlatform()
}

detekt {
  config.from(files("$rootDir/.detekt/config.yaml"))
  parallel = true
  autoCorrect = System.getenv("CI") != "true"
}

tasks.withType<Detekt> {
  exclude("org/koin/ksp/generated")
}

/**
 * By default, JAR archives are named according to the Gradle module name.
 * This causes collisions when multiple Gradle modules have the same name,
 * which can happen with nested multimodule projects.
 *
 * Here we change the archive name to be the fully-qualified project path instead,
 * which helps avoid these collisions.
 */
tasks.withType<Jar> {
  archiveBaseName = project.path.drop(1).replace(':', '-')
}
