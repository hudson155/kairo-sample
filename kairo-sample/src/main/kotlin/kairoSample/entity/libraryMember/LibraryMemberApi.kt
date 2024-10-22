package kairoSample.entity.libraryMember

import kairo.id.KairoId
import kairo.rest.endpoint.RestEndpoint

public object LibraryMemberApi {
  @RestEndpoint.Method("GET")
  @RestEndpoint.Path("/library-members/:libraryMemberId")
  @RestEndpoint.Accept("application/json")
  public data class Get(
    @PathParam val libraryMemberId: KairoId,
  ) : RestEndpoint<Nothing, LibraryMemberRep>()

  @RestEndpoint.Method("GET")
  @RestEndpoint.Path("/library-members")
  @RestEndpoint.Accept("application/json")
  public data class GetByEmailAddress(
    @QueryParam val emailAddress: String,
  ) : RestEndpoint<Nothing, LibraryMemberRep>()

  @RestEndpoint.Method("GET")
  @RestEndpoint.Path("/library-members")
  @RestEndpoint.Accept("application/json")
  public data object ListAll : RestEndpoint<Nothing, List<LibraryMemberRep>>()

  @RestEndpoint.Method("POST")
  @RestEndpoint.Path("/library-members")
  @RestEndpoint.ContentType("application/json")
  @RestEndpoint.Accept("application/json")
  public data class Create(
    override val body: LibraryMemberRep.Creator,
  ) : RestEndpoint<LibraryMemberRep.Creator, LibraryMemberRep>()

  @RestEndpoint.Method("PATCH")
  @RestEndpoint.Path("/library-members/:libraryMemberId")
  @RestEndpoint.ContentType("application/json")
  @RestEndpoint.Accept("application/json")
  public data class Update(
    @PathParam val libraryMemberId: KairoId,
    override val body: LibraryMemberRep.Update,
  ) : RestEndpoint<LibraryMemberRep.Update, LibraryMemberRep>()

  @RestEndpoint.Method("DELETE")
  @RestEndpoint.Path("/library-members/:libraryMemberId")
  @RestEndpoint.Accept("application/json")
  public data class Delete(
    @PathParam val libraryMemberId: KairoId,
  ) : RestEndpoint<Nothing, LibraryMemberRep>()
}
