package kairoSample.chat.conversation

import kairoSample.identity.user.UserId
import kotlin.time.Instant

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
