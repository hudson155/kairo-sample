package kairoSample

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import kairo.application.kairo
import kairo.dependencyInjection.DependencyInjectionFeature
import kairo.healthCheck.feature.HealthCheckFeature
import kairo.id.IdGenerationStrategy
import kairo.id.RandomIdGenerationStrategy
import kairo.rest.RestFeature
import kairo.server.Server
import kairoSample.libraryBook.LibraryBookFeature
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import org.apache.logging.log4j.LogManager
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule

fun main() {
  kairo {
    val config = loadConfig()
    val features = listOf(
      DependencyInjectionFeature {
        modules(
          defaultModule,
          module {
            single<IdGenerationStrategy> { RandomIdGenerationStrategy(length = 22) }
          },
        )
      },
      HealthCheckFeature(),
      LibraryBookFeature(),
      RestFeature(Hocon.decodeFromConfig(config.getConfig("rest"))),
    )
    val server = Server(
      name = "Kairo Sample",
      features = features,
    )
    server.startAndWait(
      release = {
        server.stop()
        LogManager.shutdown()
      },
    )
  }
}

@Suppress("ForbiddenMethodCall")
fun loadConfig(): Config {
  val configName = requireNotNull(System.getenv("CONFIG")) { "CONFIG environment variable not set." }
  return ConfigFactory.load("config/$configName.conf")
}
