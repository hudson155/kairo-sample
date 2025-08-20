package kairoSample.libraryBook

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import kairo.feature.Feature
import kairo.rest.RestFeature
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LibraryBookFeature : Feature(), RestFeature.HasRouting, KoinComponent {
  override val name: String = "Library Book"

  private val libraryBookHandler: LibraryBookHandler by inject()

  override fun Application.routing() {
    routing {
      with(libraryBookHandler) { routing() }
    }
  }
}
