package kairoSample

import com.typesafe.config.ConfigFactory
import kairo.application.kairo
import kairo.dependencyInjection.DependencyInjectionFeature
import kairo.healthCheck.HealthCheckFeature
import kairo.id.IdFeature
import kairo.rest.RestFeature
import kairo.server.Server
import kairo.sql.SqlFeature
import kairoSample.library.LibraryFeature
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import org.apache.logging.log4j.LogManager
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.koin.dsl.koinApplication

internal fun main() {
  kairo {
    val config = loadConfig()
    val koinApplication = koinApplication()
    val features = listOf(
      DependencyInjectionFeature(koinApplication),
      HealthCheckFeature(),
      IdFeature(config.id),
      LibraryFeature(koinApplication.koin),
      RestFeature(config.rest),
      SqlFeature(
        config = config.sql,
        configureDatabase = {
          explicitDialect = PostgreSQLDialect()
        },
      ),
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
private fun loadConfig(
  configName: String = requireNotNull(System.getenv("CONFIG")) { "CONFIG environment variable not set." },
): Config {
  val hocon = ConfigFactory.load("config/$configName.conf")
  return Hocon.decodeFromConfig(hocon)
}
