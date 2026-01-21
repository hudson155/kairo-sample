package kairoSample.ai

import kairo.protectedString.ProtectedString

data class AiFeatureConfig(
  val openAi: OpenAi,
) {
  data class OpenAi(
    val apiKey: ProtectedString,
  )
}
