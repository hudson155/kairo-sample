@file:Suppress("ForbiddenImport")

package kairoSample

import com.typesafe.config.ConfigFactory
import kairo.application.kairo
import kairo.dependencyInjection.DependencyInjectionFeature
import kairo.healthCheck.HealthCheck
import kairo.healthCheck.HealthCheckFeature
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

    val healthChecks = mapOf(
      "sql" to HealthCheck { SqlFeature.healthCheck(koinApplication.koin.get()) },
    )

    val features = listOf(
      DependencyInjectionFeature(koinApplication),
      HealthCheckFeature(healthChecks),
      LibraryFeature(koinApplication.koin),
      RestFeature(
        config = config.rest,
        authConfig = null,
      ),
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
private fun loadConfig(): Config {
  val configName = requireNotNull(System.getenv("CONFIG")) { "CONFIG environment variable not set." }
  return ConfigFactory.load("config/$configName.conf")
    .let { Hocon.decodeFromConfig<Config>(it) }
}
