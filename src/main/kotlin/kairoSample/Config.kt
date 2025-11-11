package kairoSample

import kairo.rest.RestFeatureConfig
import kairo.sql.SqlFeatureConfig
import kotlinx.serialization.Serializable

@Serializable
data class Config(
  val rest: RestFeatureConfig,
  val sql: SqlFeatureConfig,
)
