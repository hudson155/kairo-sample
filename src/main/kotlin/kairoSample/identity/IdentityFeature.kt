package kairoSample.identity

import io.ktor.server.application.Application
import kairo.dependencyInjection.HasKoinModules
import kairo.feature.Feature
import kairo.rest.HasRouting
import kairoSample.identity.user.UserHandler
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.ksp.generated.module

@org.koin.core.annotation.Module
@org.koin.core.annotation.ComponentScan
class IdentityFeature(
  private val koin: Koin,
) : Feature(), HasKoinModules, HasRouting {
  override val name: String = "Identity"

  private val userHandler: UserHandler get() = koin.get()

  override val koinModules: List<Module> = listOf(module)

  override fun Application.routing() {
    with(userHandler) { routing() }
  }
}
