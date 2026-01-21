package kairoSample.chat.conversation.exception

import io.ktor.http.HttpStatusCode
import kairo.exception.LogicalFailure
import kairoSample.chat.conversation.ConversationId

class ConversationNotFound private constructor(
  override val status: HttpStatusCode,
  val conversationId: ConversationId?,
) : LogicalFailure("Conversation not found") {
  override val type: String = "ConversationNotFound"

  constructor(conversationId: ConversationId?) : this(
    status = HttpStatusCode.NotFound,
    conversationId = conversationId,
  )

  override fun MutableMap<String, Any?>.buildJson() {
    conversationId?.let { put("conversationId", it) }
  }

  companion object {
    fun unprocessable(conversationId: ConversationId): ConversationNotFound =
      ConversationNotFound(
        status = HttpStatusCode.UnprocessableEntity,
        conversationId = conversationId,
      )
  }
}
