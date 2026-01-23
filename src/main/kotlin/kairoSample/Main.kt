package kairoSample

import kairo.application.kairo
import kairo.config.ConfigResolver
import kairo.config.loadConfig
import kairo.dependencyInjection.DependencyInjectionFeature
import kairo.gcpSecretSupplier.DefaultGcpSecretSupplier
import kairo.gcpSecretSupplier.GcpSecretSupplier
import kairo.healthCheck.HealthCheck
import kairo.healthCheck.HealthCheckFeature
import kairo.protectedString.ProtectedString
import kairo.rest.RestFeature
import kairo.rest.auth.koin
import kairo.serialization.KairoJson
import kairo.server.Server
import kairo.sql.SqlFeature
import kairo.stytch.StytchFeature
import kairoSample.ai.AiFeature
import kairoSample.chat.ChatFeature
import kairoSample.identity.IdentityFeature
import org.apache.logging.log4j.LogManager
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.koin.dsl.koinApplication
import osiris.defaultModel
import osiris.openAi

private val gcpSecretSupplier: GcpSecretSupplier = DefaultGcpSecretSupplier()

@OptIn(ProtectedString.Access::class)
fun main() {
  kairo {
    val config = loadConfig<Config>(
      json = json,
      resolvers = listOf(
        ConfigResolver("gcp::") { gcpSecretSupplier[it]?.value },
      ),
    )

    val koinApplication = koinApplication()

    val healthChecks = mapOf(
      "sql" to HealthCheck { SqlFeature.healthCheck(koinApplication.koin.get()) },
    )

    val features = listOf(
      AiFeature(config.ai),
      ChatFeature(koinApplication.koin) { modelFactory ->
        defaultModel = modelFactory.openAi("gpt-5.2")
      },
      DependencyInjectionFeature(koinApplication),
      HealthCheckFeature(healthChecks),
      IdentityFeature(koinApplication.koin),
      RestFeature(
        config = config.rest,
        authConfig = null,
        json = KairoJson {
          configure()
          pretty = true
        },
        ktorModule = {
          koin = koinApplication.koin
        },
      ),
      SqlFeature(
        config = config.sql,
        configureDatabase = {
          explicitDialect = PostgreSQLDialect()
        },
      ),
      StytchFeature(config.stytch),
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
