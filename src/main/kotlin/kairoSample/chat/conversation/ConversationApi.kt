package kairoSample.chat.conversation

import kairo.rest.Rest
import kairo.rest.RestEndpoint
import kairoSample.identity.user.UserId

object ConversationApi {
  @Rest("GET", "/conversations/:conversationId")
  @Rest.Accept("application/json")
  data class Get(
    @PathParam val conversationId: ConversationId,
  ) : RestEndpoint<Unit, ConversationRep>()

  @Rest("GET", "/conversations")
  @Rest.Accept("application/json")
  data class ListByUser(
    @QueryParam val userId: UserId,
  ) : RestEndpoint<Unit, List<ConversationRep>>()

  @Rest("POST", "/conversations")
  @Rest.ContentType("application/json")
  @Rest.Accept("application/json")
  data class Create(
    override val body: ConversationRep.Creator,
  ) : RestEndpoint<ConversationRep.Creator, ConversationRep>()

  @Rest("DELETE", "/conversations/:conversationId")
  @Rest.Accept("application/json")
  data class Delete(
    @PathParam val conversationId: ConversationId,
  ) : RestEndpoint<Unit, ConversationRep>()
}
