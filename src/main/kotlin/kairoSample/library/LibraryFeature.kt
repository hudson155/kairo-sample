package kairoSample.library

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import kairo.feature.Feature
import kairo.rest.HasRouting
import kairoSample.library.libraryBook.LibraryBookHandler
import org.koin.core.Koin

class LibraryFeature(
  private val koin: Koin,
) : Feature(), HasRouting {
  override val name: String = "Library"

  private val libraryBookHandler: LibraryBookHandler get() = koin.get()

  override fun Application.routing() {
    routing {
      with(libraryBookHandler) { routing() }
    }
  }
}
