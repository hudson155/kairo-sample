import java.net.URI
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.jvm.toolchain.JavaLanguageVersion

val javaVersion: JavaLanguageVersion = JavaLanguageVersion.of(21)

fun RepositoryHandler.artifactRegistry() {
  maven {
    url = URI("artifactregistry://us-central1-maven.pkg.dev/airborne-software/maven")
  }
}
