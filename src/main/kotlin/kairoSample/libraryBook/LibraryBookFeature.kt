package kairoSample.libraryBook

import io.ktor.server.application.Application
import kairo.dependencyInjection.HasKoinModules
import kairo.feature.Feature
import kairo.rest.HasRouting
import kairo.rest.Routing
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.ksp.generated.module

@org.koin.core.annotation.Module
@org.koin.core.annotation.ComponentScan
class LibraryBookFeature(
  private val koin: Koin,
) : Feature(), HasKoinModules, HasRouting {
  override val name: String = "Library Book"

  private val libraryBookHandler: LibraryBookHandler get() = koin.get()

  override val koinModules: List<Module> = listOf(module)

  @Routing
  override fun Application.routing() {
    with(libraryBookHandler) { routing() }
  }
}
