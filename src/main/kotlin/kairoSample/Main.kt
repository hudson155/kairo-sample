package kairoSample

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import kairo.application.kairo
import kairo.healthCheck.feature.HealthCheckFeature
import kairo.rest.RestFeature
import kairo.server.Server
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import org.apache.logging.log4j.LogManager
import org.koin.ksp.generated.defaultModule

// TODO: Log4j2.xml to support GCP.

fun main() {
  kairo {
    val config = loadConfig()
    val features = listOf(
      DependencyInjectionFeature {
        modules(
          defaultModule,
          module {
            single<IdGenerationStrategy> { RandomIdGenerationStrategy(length = 24) }
          },
        )
      },
      HealthCheckFeature(),
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
