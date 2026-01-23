package kairoSample.chat.agent

import io.ktor.util.AttributeKey
import kairoSample.chat.conversation.ConversationModel
import kairoSample.identity.user.UserId
import org.koin.core.annotation.Single
import osiris.Context
import osiris.ModelFactory
import osiris.context
import osiris.history

@Single
class ContextFactory(
  private val historyFactory: HistoryFactory,
  private val modelFactory: ModelFactory,
  private val configureContext: ConfigureContext,
) {
  fun create(conversation: ConversationModel): Context =
    context {
      userId = conversation.userId
      history = historyFactory.History(conversation.id)
      configureContext(modelFactory)
    }
}

typealias ConfigureContext = Context.(modelFactory: ModelFactory) -> Unit

private val userIdKey: AttributeKey<UserId> = AttributeKey("userId")

var Context.userId: UserId
  get() = attributes[userIdKey]
  set(value) {
    attributes[userIdKey] = value
  }
