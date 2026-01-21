package kairoSample.chat.conversation

import kotlin.time.Instant
import kairoSample.identity.user.UserId

data class ConversationModel(
  val id: ConversationId,
  val createdAt: Instant,
  val userId: UserId,
  val agentName: String,
  val processing: Boolean, // TODO: Right now, this is always false.
) {
  data class Creator(
    val userId: UserId,
    val agentName: String,
    val processing: Boolean,
  ) {
    companion object
  }

  companion object
}
