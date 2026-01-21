package kairoSample.chat.message

import kairo.rest.Rest
import kairo.rest.RestEndpoint
import kairoSample.chat.conversation.ConversationId

object MessageApi {
  @Rest("GET", "/messages/:messageId")
  @Rest.Accept("application/json")
  data class Get(
    @PathParam val messageId: MessageId,
  ) : RestEndpoint<Unit, MessageRep>()

  @Rest("GET", "/messages")
  @Rest.Accept("application/json")
  data class ListByConversation(
    @QueryParam val conversationId: ConversationId,
  ) : RestEndpoint<Unit, List<MessageRep>>()

  @Rest("POST", "/messages")
  @Rest.ContentType("application/json")
  @Rest.Accept("application/json")
  data class Create(
    override val body: MessageRep.Creator,
  ) : RestEndpoint<MessageRep.Creator, MessageRep>()

  @Rest("DELETE", "/messages/:messageId")
  @Rest.Accept("application/json")
  data class Delete(
    @PathParam val messageId: MessageId,
  ) : RestEndpoint<Unit, MessageRep>()
}
