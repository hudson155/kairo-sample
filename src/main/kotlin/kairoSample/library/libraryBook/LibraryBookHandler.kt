package kairoSample.library.libraryBook

import io.ktor.server.application.Application
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.routing.routing
import kairo.rest.HasRouting
import kairo.rest.route
import org.koin.core.annotation.Single

@Single
class LibraryBookHandler(
  private val libraryBookMapper: LibraryBookMapper,
  private val libraryBookService: LibraryBookService,
) : HasRouting {
  override fun Application.routing() {
    routing {
      route(LibraryBookApi.Get::class) {
        handle { endpoint ->
          val libraryBook = libraryBookService.get(endpoint.libraryBookId)
            ?: throw NotFoundException() // TODO: Use a more specific 404.
          libraryBookMapper.rep(libraryBook)
        }
      }

      route(LibraryBookApi.ListAll::class) {
        handle { _ ->
          val libraryBooks = libraryBookService.listAll()
          libraryBooks.map { libraryBookMapper.rep(it) }
        }
      }

      route(LibraryBookApi.Create::class) {
        handle { endpoint ->
          val creator = endpoint.body
          val libraryBook = libraryBookService.create(
            creator = libraryBookMapper.creator(creator),
          )
          libraryBookMapper.rep(libraryBook)
        }
      }
    }
  }
}
