package kairoSample.libraryBook

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import kairo.rest.HasRouting
import kairo.rest.Routing
import kairo.rest.route
import kairoSample.libraryBook.exception.LibraryBookNotFound
import org.koin.core.annotation.Single

@Single
class LibraryBookHandler(
  private val libraryBookMapper: LibraryBookMapper,
  private val libraryBookService: LibraryBookService,
) : HasRouting {
  @Routing
  override fun Application.routing() {
    routing {
      route(LibraryBookApi.Get::class) {
        handle {
          val libraryBook = libraryBookService.get(endpoint.libraryBookId)
            ?: throw LibraryBookNotFound(endpoint.libraryBookId)
          libraryBookMapper.rep(libraryBook)
        }
      }

      route(LibraryBookApi.GetByIsbn::class) {
        handle {
          val libraryBook = libraryBookService.getByIsbn(endpoint.isbn)
            ?: throw LibraryBookNotFound(null)
          libraryBookMapper.rep(libraryBook)
        }
      }

      route(LibraryBookApi.ListAll::class) {
        handle {
          val libraryBooks = libraryBookService.listAll()
          libraryBooks.map { libraryBookMapper.rep(it) }
        }
      }

      route(LibraryBookApi.Create::class) {
        handle {
          val creator = endpoint.body
          val libraryBook = libraryBookService.create(
            creator = libraryBookMapper.creator(creator),
          )
          libraryBookMapper.rep(libraryBook)
        }
      }

      route(LibraryBookApi.Update::class) {
        handle {
          val libraryBook = libraryBookService.update(
            id = endpoint.libraryBookId,
            update = libraryBookMapper.update(endpoint.body),
          )
          libraryBookMapper.rep(libraryBook)
        }
      }

      route(LibraryBookApi.Delete::class) {
        handle {
          val libraryBook = libraryBookService.delete(endpoint.libraryBookId)
          libraryBookMapper.rep(libraryBook)
        }
      }
    }
  }
}
