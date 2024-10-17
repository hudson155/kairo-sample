package kairoSample.entity.userAccount

import kairo.id.KairoId
import kairo.rest.endpoint.RestEndpoint

public object UserAccountApi {
  @RestEndpoint.Method("GET")
  @RestEndpoint.Path("/user-accounts/:userAccountId")
  @RestEndpoint.Accept("application/json")
  public data class Get(
    @PathParam val userAccountId: KairoId,
  ) : RestEndpoint<Nothing, UserAccountRep>()

  @RestEndpoint.Method("POST")
  @RestEndpoint.Path("/user-accounts")
  @RestEndpoint.ContentType("application/json")
  @RestEndpoint.Accept("application/json")
  public data class Create(
    override val body: UserAccountRep.Creator,
  ) : RestEndpoint<UserAccountRep.Creator, UserAccountRep>()
}
