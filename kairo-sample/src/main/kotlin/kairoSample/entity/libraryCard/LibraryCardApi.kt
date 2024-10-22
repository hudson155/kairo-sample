package kairoSample.entity.libraryCard

import kairo.id.KairoId
import kairo.rest.endpoint.RestEndpoint

public object LibraryCardApi {
  @RestEndpoint.Method("GET")
  @RestEndpoint.Path("/library-cards/:libraryCardId")
  @RestEndpoint.Accept("application/json")
  public data class Get(
    @PathParam val libraryCardId: KairoId,
  ) : RestEndpoint<Nothing, LibraryCardRep>()

  @RestEndpoint.Method("GET")
  @RestEndpoint.Path("/library-cards")
  @RestEndpoint.Accept("application/json")
  public data object ListAll : RestEndpoint<Nothing, List<LibraryCardRep>>()

  @RestEndpoint.Method("GET")
  @RestEndpoint.Path("/library-cards")
  @RestEndpoint.Accept("application/json")
  public data class ListByLibraryMember(
    @QueryParam val libraryMemberId: KairoId,
  ) : RestEndpoint<Nothing, List<LibraryCardRep>>()

  @RestEndpoint.Method("POST")
  @RestEndpoint.Path("/library-cards")
  @RestEndpoint.ContentType("application/json")
  @RestEndpoint.Accept("application/json")
  public data class Create(
    override val body: LibraryCardRep.Creator,
  ) : RestEndpoint<LibraryCardRep.Creator, LibraryCardRep>()

  @RestEndpoint.Method("DELETE")
  @RestEndpoint.Path("/library-cards/:libraryCardId")
  @RestEndpoint.Accept("application/json")
  public data class Delete(
    @PathParam val libraryCardId: KairoId,
  ) : RestEndpoint<Nothing, LibraryCardRep>()
}
