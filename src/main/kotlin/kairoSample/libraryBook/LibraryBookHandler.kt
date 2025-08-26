package kairoSample.libraryBook

import io.ktor.server.application.Application
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.routing.routing
import kairo.rest.HasRouting
import kairo.rest.route
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Single(createdAtStart = true)
class LibraryBookHandler : HasRouting, KoinComponent {
  private val libraryBookMapper: LibraryBookMapper by inject()
  private val libraryBookService: LibraryBookService by inject()

  override fun Application.routing() {
    routing {
      route(LibraryBookApi.Get::class) {
        handle { endpoint ->
          val libraryBook = libraryBookService.get(endpoint.libraryBookId)
            ?: throw NotFoundException() // TODO: Use a more specific 404.
          return@handle libraryBookMapper.rep(libraryBook)
        }
      }

      route(LibraryBookApi.ListAll::class) {
        handle { _ ->
          val libraryBooks = libraryBookService.listAll()
          return@handle libraryBooks.map { libraryBookMapper.rep(it) }
        }
      }

      route(LibraryBookApi.Create::class) {
        handle { endpoint ->
          val creator = endpoint.body
          val libraryBook = libraryBookService.create(
            creator = libraryBookMapper.creator(creator),
          )
          return@handle libraryBookMapper.rep(libraryBook)
        }
      }
    }
  }
}
