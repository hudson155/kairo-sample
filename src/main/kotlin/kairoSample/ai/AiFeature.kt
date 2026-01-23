package kairoSample.ai

import kairo.dependencyInjection.HasKoinModules
import kairo.feature.Feature
import org.koin.core.module.Module
import org.koin.dsl.module
import osiris.ModelFactory
import osiris.modelFactory
import osiris.openAiApiKey

class AiFeature(
  private val config: AiFeatureConfig,
) : Feature(), HasKoinModules {
  override val name: String = "AI"

  override val koinModules: List<Module> =
    listOf(
      module {
        single<ModelFactory> {
          modelFactory {
            config.openAi?.let { openAiApiKey = it.apiKey }
          }
        }
      },
    )
}
