package kairoSample.libraryBook

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import kairo.feature.Feature
import kairo.rest.HasRouting
import org.koin.core.Koin

class LibraryBookFeature(
  private val koin: Koin,
) : Feature(), HasRouting {
  override val name: String = "Library Book"

  private val libraryBookHandler: LibraryBookHandler
    get() = koin.get()

  override fun Application.routing() {
    routing {
      with(libraryBookHandler) { routing() }
    }
  }
}
