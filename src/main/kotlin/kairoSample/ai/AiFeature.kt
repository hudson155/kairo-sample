package kairoSample.ai

import kairo.dependencyInjection.HasKoinModules
import kairo.feature.Feature
import org.koin.core.module.Module
import org.koin.dsl.module
import osiris.Context
import osiris.ModelFactory
import osiris.context
import osiris.defaultModel
import osiris.modelFactory
import osiris.openAi
import osiris.openAiApiKey

class AiFeature(
  private val config: AiFeatureConfig,
) : Feature(), HasKoinModules {
  override val name: String = "AI"

  override val koinModules: List<Module> =
    listOf(
      module {
        factory<Context> {
          context {
            defaultModel = get<ModelFactory>().openAi("gpt-5.2")
          }
        }
        single<ModelFactory> {
          modelFactory {
            openAiApiKey = config.openAi.apiKey
          }
        }
      },
    )
}
