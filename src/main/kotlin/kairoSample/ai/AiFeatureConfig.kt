package kairoSample.ai

import kairo.protectedString.ProtectedString

data class AiFeatureConfig(
  val openAi: OpenAi? = null,
) {
  data class OpenAi(
    val apiKey: ProtectedString,
  )
}
