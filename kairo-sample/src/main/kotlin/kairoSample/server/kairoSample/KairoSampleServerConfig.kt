package kairoSample.server.kairoSample

import kairo.clock.KairoClockConfig
import kairo.id.KairoIdConfig
import kairo.logging.KairoLoggingConfig
import kairo.rest.KairoRestConfig
import kairo.rest.cors.KairoCorsConfig
import kairo.server.FeatureManagerConfig
import kairo.sql.KairoSqlConfig
import kairo.sqlMigration.KairoSqlMigrationConfig

public data class KairoSampleServerConfig(
  val auth: Auth,
  val clock: KairoClockConfig,
  val cors: KairoCorsConfig,
  val featureManager: FeatureManagerConfig,
  val id: KairoIdConfig,
  val logging: KairoLoggingConfig,
  val rest: KairoRestConfig,
  val sql: KairoSqlConfig,
  val sqlMigration: KairoSqlMigrationConfig,
) {
  public data class Auth(
    val jwtIssuer: String,
    val jwksEndpoint: String,
    val leewaySec: Long,
  )
}
