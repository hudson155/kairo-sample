package kairoSample.libraryBook

import kairo.rest.Rest
import kairo.rest.RestEndpoint

object LibraryBookApi {
  @Rest("GET", "/library-books/:libraryBookId")
  @Rest.Accept("application/json")
  data class Get(
    @PathParam val libraryBookId: LibraryBookId,
  ) : RestEndpoint<Unit, LibraryBookRep>()

  @Rest("GET", "/library-books")
  @Rest.Accept("application/json")
  data object ListAll : RestEndpoint<Unit, List<LibraryBookRep>>()

  @Rest("POST", "/library-books")
  @Rest.ContentType("application/json")
  @Rest.Accept("application/json")
  data class Create(
    override val body: LibraryBookRep.Creator,
  ) : RestEndpoint<LibraryBookRep.Creator, LibraryBookRep>()

  // TODO: Add additional endpoints.
}
