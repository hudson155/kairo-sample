package kairoSample.chat.conversation

import kotlin.time.Instant
import kairoSample.identity.user.UserId

data class ConversationRep(
  val id: ConversationId,
  val createdAt: Instant,
  val userId: UserId,
  val agentName: String,
  val state: State,
) {
  data class State(
    val canSendMessage: Boolean,
  )

  data class Creator(
    val userId: UserId,
    val agentName: String,
  )
}
