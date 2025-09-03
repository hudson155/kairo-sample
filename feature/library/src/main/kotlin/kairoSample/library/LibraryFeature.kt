package kairoSample.library

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import kairo.dependencyInjection.KoinModule
import kairo.feature.Feature
import kairo.rest.HasRouting
import kairoSample.library.libraryBook.LibraryBookHandler
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.ksp.generated.kairosample_library_LibraryFeature

@org.koin.core.annotation.Module
@org.koin.core.annotation.ComponentScan
public class LibraryFeature(
  private val koin: Koin,
) : Feature(), KoinModule, HasRouting {
  override val name: String = "Library"

  private val libraryBookHandler: LibraryBookHandler get() = koin.get()

  override val koinModule: Module = kairosample_library_LibraryFeature

  override fun Application.routing() {
    routing {
      with(libraryBookHandler) { routing() }
    }
  }
}
