package kairoSample

import kairo.id.IdFeatureConfig
import kairo.rest.RestFeatureConfig
import kairo.sql.SqlFeatureConfig
import kotlinx.serialization.Serializable

@Serializable
public data class Config(
  val id: IdFeatureConfig = IdFeatureConfig(),
  val rest: RestFeatureConfig,
  val sql: SqlFeatureConfig,
)
