package kairoSample.library.libraryBook

import kairo.rest.Rest
import kairo.rest.RestEndpoint

internal object LibraryBookApi {
  @Rest("GET", "/library-books/:libraryBookId")
  @Rest.Accept("application/json")
  internal data class Get(
    @PathParam val libraryBookId: LibraryBookId,
  ) : RestEndpoint<Unit, LibraryBookRep>()

  @Rest("GET", "/library-books")
  @Rest.Accept("application/json")
  internal data class GetByIsbn(
    @QueryParam val isbn: String,
  ) : RestEndpoint<Unit, LibraryBookRep>()

  @Rest("GET", "/library-books")
  @Rest.Accept("application/json")
  internal data object ListAll : RestEndpoint<Unit, List<LibraryBookRep>>()

  @Rest("POST", "/library-books")
  @Rest.ContentType("application/json")
  @Rest.Accept("application/json")
  internal data class Create(
    override val body: LibraryBookRep.Creator,
  ) : RestEndpoint<LibraryBookRep.Creator, LibraryBookRep>()

  @Rest("PATCH", "/library-books/:libraryBookId")
  @Rest.ContentType("application/json")
  @Rest.Accept("application/json")
  internal data class Update(
    @PathParam val libraryBookId: LibraryBookId,
    override val body: LibraryBookRep.Update,
  ) : RestEndpoint<LibraryBookRep.Update, LibraryBookRep>()
}
