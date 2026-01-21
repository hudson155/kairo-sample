package kairoSample.chat.message

import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.message.ToolExecutionResultMessage
import dev.langchain4j.data.message.UserMessage
import kotlin.time.Instant
import osiris.element.element.Element
import kairoSample.chat.conversation.ConversationId
import kairoSample.identity.user.UserId

data class MessageModel(
  val id: MessageId,
  val createdAt: Instant,
  val userId: UserId,
  val conversationId: ConversationId,
  val author: MessageRep.Author,
  val raw: ChatMessage,
  val elements: List<Element>,
) {
  data class Creator(
    val conversationId: ConversationId,
    val author: MessageRep.Author,
    val raw: ChatMessage,
  ) {
    init {
      checkAuthor(author, raw)
    }

    companion object
  }

  init {
    checkAuthor(author, raw)
  }

  companion object {
    private fun checkAuthor(author: MessageRep.Author, raw: ChatMessage) {
      when (raw) {
        is AiMessage ->
          check(author is MessageRep.Author.Agent) { "AI messages must be agent-authored." }
        is ToolExecutionResultMessage ->
          check(author is MessageRep.Author.Agent) { "Tool execution result messages must be agent-authored." }
        is UserMessage ->
          check(author is MessageRep.Author.User) { "User messages must be user-authored." }
        else -> error("Unsupported message type: ${raw::class.simpleName}.")
      }
    }
  }
}
