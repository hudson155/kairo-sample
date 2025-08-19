package kairoSample.libraryBook

import io.ktor.server.application.Application
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import kairo.rest.RestFeature
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Single(createdAtStart = true)
internal class LibraryBookHandler : RestFeature.HasRouting, KoinComponent {
  private val libraryBookMapper: LibraryBookMapper by inject()
  private val libraryBookService: LibraryBookService by inject()

  override fun Application.routing() {
    routing {
      get<LibraryBookApi.Id> { api ->
        val libraryBook = libraryBookService.get(api.id) ?: throw NotFoundException() // TODO: Use a more specific 404.
        call.respond(libraryBookMapper.rep(libraryBook))
      }
      get<LibraryBookApi> {
        val libraryBooks = libraryBookService.listAll()
        call.respond(libraryBooks.map { libraryBookMapper.rep(it) })
      }
      post<LibraryBookApi> {
        val creator = call.receive<LibraryBookRep.Creator>()
          .let { libraryBookMapper.creator(it) }
        val libraryBook = libraryBookService.create(creator)
        call.respond(libraryBookMapper.rep(libraryBook))
      }
    }
  }
}
