package kairoSample.chat.message.exception

import io.ktor.http.HttpStatusCode
import kairo.exception.LogicalFailure
import kairoSample.chat.message.MessageId

class MessageNotFound private constructor(
  override val status: HttpStatusCode,
  val messageId: MessageId?,
) : LogicalFailure("Message not found") {
  override val type: String = "MessageNotFound"

  constructor(messageId: MessageId?) : this(
    status = HttpStatusCode.NotFound,
    messageId = messageId,
  )

  override fun MutableMap<String, Any?>.buildJson() {
    messageId?.let { put("messageId", it) }
  }

  companion object {
    fun unprocessable(messageId: MessageId): MessageNotFound =
      MessageNotFound(
        status = HttpStatusCode.UnprocessableEntity,
        messageId = messageId,
      )
  }
}
