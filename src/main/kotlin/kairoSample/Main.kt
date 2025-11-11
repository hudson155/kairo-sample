@file:Suppress("ForbiddenImport")

package kairoSample

import com.typesafe.config.ConfigFactory
import kairo.application.kairo
import kairo.config.ConfigResolver
import kairo.config.resolveConfig
import kairo.dependencyInjection.DependencyInjectionFeature
import kairo.gcpSecretSupplier.DefaultGcpSecretSupplier
import kairo.gcpSecretSupplier.GcpSecretSupplier
import kairo.healthCheck.HealthCheck
import kairo.healthCheck.HealthCheckFeature
import kairo.protectedString.ProtectedString
import kairo.rest.RestFeature
import kairo.server.Server
import kairo.sql.SqlFeature
import kairoSample.libraryBook.LibraryBookFeature
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import org.apache.logging.log4j.LogManager
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.koin.dsl.koinApplication

private val gcpSecretSupplier: GcpSecretSupplier = DefaultGcpSecretSupplier()

fun main() {
  kairo {
    val config = loadConfig()

    val koinApplication = koinApplication()

    val healthChecks = mapOf(
      "sql" to HealthCheck { SqlFeature.healthCheck(koinApplication.koin.get()) },
    )

    val features = listOf(
      DependencyInjectionFeature(koinApplication),
      HealthCheckFeature(healthChecks),
      LibraryBookFeature(koinApplication.koin),
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
@OptIn(ProtectedString.Access::class)
private suspend fun loadConfig(): Config {
  val configName = requireNotNull(System.getenv("CONFIG")) { "CONFIG environment variable not set." }
  val configResolvers = listOf(
    ConfigResolver("gcp::") { gcpSecretSupplier[it]?.value },
  )
  return ConfigFactory.load("config/$configName.conf")
    .let { Hocon.decodeFromConfig<Config>(it) }
    .let { resolveConfig(it, configResolvers) }
}
