package kairoSample.db

import com.typesafe.config.ConfigFactory
import kairo.config.ConfigResolver
import kairo.config.resolveConfig
import kairo.gcpSecretSupplier.DefaultGcpSecretSupplier
import kairo.gcpSecretSupplier.GcpSecretSupplier
import kairo.protectedString.ProtectedString
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig

private val gcpSecretSupplier: GcpSecretSupplier = DefaultGcpSecretSupplier()

@Serializable
data class DatabaseConfig(
  val url: String,
  val username: String,
  val password: ProtectedString,
)

@Suppress("ForbiddenMethodCall")
@OptIn(ProtectedString.Access::class)
val databaseConfig: DatabaseConfig =
  runBlocking {
    val configName = System.getenv("DATABASE_CONFIG") ?: "local"
    val configResolvers = listOf(
      ConfigResolver("gcp::") { gcpSecretSupplier[it]?.value },
    )
    return@runBlocking ConfigFactory.load("databaseConfig/$configName.conf")
      .let { Hocon.decodeFromConfig<DatabaseConfig>(it) }
      .let { resolveConfig(it, configResolvers) }
  }
