package kairoSample.chat.agent

import io.ktor.util.AttributeKey
import kairoSample.chat.conversation.ConversationModel
import kairoSample.identity.user.UserId
import org.koin.core.annotation.Single
import osiris.Context
import osiris.history

@Single
class ContextFactory(
  private val historyFactory: HistoryFactory,
) {
  fun create(conversation: ConversationModel): Context =
    Context().apply {
      userId = conversation.userId
      history = historyFactory.History(conversation.id)
    }
}

private val userIdKey: AttributeKey<UserId> = AttributeKey("userId")

var Context.userId: UserId
  get() = attributes[userIdKey]
  set(value) {
    attributes[userIdKey] = value
  }
