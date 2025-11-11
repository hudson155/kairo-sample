@file:Suppress("MatchingDeclarationName")

import java.net.URI
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.jvm.toolchain.JavaLanguageVersion

val javaVersion: JavaLanguageVersion = JavaLanguageVersion.of(21)

fun RepositoryHandler.artifactRegistry() {
  maven {
    url = URI("artifactregistry://us-central1-maven.pkg.dev/airborne-software/maven")
  }
}

object Airborne {
  // https://github.com/hudson155/kairo/releases
  const val kairo: String = "software.airborne.kairo:bom-full:6.0.0-beta.6"
}
