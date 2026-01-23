package kairoSample.db

import kairo.config.ConfigResolver
import kairo.config.configName
import kairo.config.loadConfig
import kairo.gcpSecretSupplier.DefaultGcpSecretSupplier
import kairo.gcpSecretSupplier.GcpSecretSupplier
import kairo.protectedString.ProtectedString
import kairoSample.json
import kotlinx.coroutines.runBlocking

private val gcpSecretSupplier: GcpSecretSupplier = DefaultGcpSecretSupplier()

data class DatabaseConfig(
  val url: String,
  val username: String,
  val password: ProtectedString,
)

@OptIn(ProtectedString.Access::class)
val databaseConfig: DatabaseConfig =
  runBlocking {
    val configName = configName(
      configName = requireNotNull(System.getenv("DATABASE_CONFIG")) { "DATABASE_CONFIG environment variable not set." },
      prefix = "databaseConfig",
    )
    loadConfig(
      configName = configName,
      json = json,
      resolvers = listOf(
        ConfigResolver("gcp::") { gcpSecretSupplier[it]?.value },
      ),
    )
  }
