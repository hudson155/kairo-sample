package kairoSample.chat

import kairo.dependencyInjection.HasKoinModules
import kairo.feature.Feature
import org.koin.core.module.Module
import org.koin.ksp.generated.module

@org.koin.core.annotation.Module
@org.koin.core.annotation.ComponentScan
class ChatTestFeature : Feature(), HasKoinModules {
  override val name: String = "Chat (Test)"

  override val koinModules: List<Module> = listOf(module)
}
