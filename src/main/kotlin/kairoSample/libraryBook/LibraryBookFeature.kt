package kairoSample.libraryBook

import io.ktor.server.application.Application
import kairo.dependencyInjection.HasKoinModules
import kairo.feature.Feature
import kairo.rest.HasRouting
import kairo.rest.Routing
import org.koin.core.Koin
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

@Module
@ComponentScan
public class LibraryBookFeature(
  private val koin: Koin,
) : Feature(), HasKoinModules, HasRouting {
  override val name: String = "Library"

  private val libraryBookHandler: LibraryBookHandler get() = koin.get()

  override val koinModules: List<org.koin.core.module.Module> = listOf(module)

  @Routing
  override fun Application.routing() {
    with(libraryBookHandler) { routing() }
  }
}
