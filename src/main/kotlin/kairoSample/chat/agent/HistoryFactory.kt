package kairoSample.chat.agent

import dev.langchain4j.data.message.ChatMessage
import org.koin.core.annotation.Single
import osiris.Context
import osiris.currentAgent
import kairoSample.chat.conversation.ConversationId
import kairoSample.chat.message.MessageModel
import kairoSample.chat.message.MessageRep
import kairoSample.chat.message.MessageService

@Single
class HistoryFactory(
  messageService: Lazy<MessageService>,
) {
  private val messageService: MessageService by messageService

  inner class History(
    val conversationId: ConversationId,
  ) : osiris.History() {
    context(context: Context)
    override suspend fun get(): List<ChatMessage> =
      messageService.listByConversation(conversationId).asReversed().map { it.raw }

    context(context: Context)
    override suspend fun append(messages: List<ChatMessage>) {
      val agent = checkNotNull(context.currentAgent)
      messages.forEach { message ->
        messageService.create(
          creator = MessageModel.Creator(
            conversationId = conversationId,
            author = MessageRep.Author.Agent(
              name = agent.name,
            ),
            raw = message,
          ),
        )
      }
    }
  }
}
