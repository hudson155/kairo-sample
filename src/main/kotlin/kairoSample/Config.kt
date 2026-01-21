package kairoSample

import kairo.rest.RestFeatureConfig
import kairo.sql.SqlFeatureConfig
import kairo.stytch.StytchFeatureConfig
import kairoSample.ai.AiFeatureConfig

data class Config(
  val ai: AiFeatureConfig,
  val rest: RestFeatureConfig,
  val sql: SqlFeatureConfig,
  val stytch: StytchFeatureConfig,
)
