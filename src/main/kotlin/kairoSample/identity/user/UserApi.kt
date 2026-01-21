package kairoSample.identity.user

import kairo.rest.Rest
import kairo.rest.RestEndpoint

object UserApi {
  @Rest("GET", "/users/:userId")
  @Rest.Accept("application/json")
  data class Get(
    @PathParam val userId: UserId,
  ) : RestEndpoint<Unit, UserRep>()

  @Rest("GET", "/users")
  @Rest.Accept("application/json")
  data object ListAll : RestEndpoint<Unit, List<UserRep>>()

  @Rest("POST", "/users")
  @Rest.ContentType("application/json")
  @Rest.Accept("application/json")
  data class Create(
    override val body: UserRep.Creator,
  ) : RestEndpoint<UserRep.Creator, UserRep>()

  @Rest("PATCH", "/users/:userId")
  @Rest.ContentType("application/json")
  @Rest.Accept("application/json")
  data class Update(
    @PathParam val userId: UserId,
    override val body: UserRep.Update,
  ) : RestEndpoint<UserRep.Update, UserRep>()

  @Rest("DELETE", "/users/:userId")
  @Rest.Accept("application/json")
  data class Delete(
    @PathParam val userId: UserId,
  ) : RestEndpoint<Unit, UserRep>()
}
