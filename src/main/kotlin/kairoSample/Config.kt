package kairoSample

import kairo.id.IdFeatureConfig
import kairo.rest.RestFeatureConfig
import kotlinx.serialization.Serializable

@Serializable
data class Config(
  val id: IdFeatureConfig = IdFeatureConfig(),
  val rest: RestFeatureConfig,
)
