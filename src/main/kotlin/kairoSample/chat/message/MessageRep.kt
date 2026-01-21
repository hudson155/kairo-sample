package kairoSample.chat.message

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.JsonNode
import kotlin.time.Instant
import osiris.element.element.Element
import kairoSample.chat.conversation.ConversationId
import kairoSample.identity.user.UserId

data class MessageRep(
  val id: MessageId,
  val createdAt: Instant,
  val userId: UserId,
  val conversationId: ConversationId,
  val author: Author,
  val raw: JsonNode,
  val elements: List<Element>,
) {
  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
  @JsonSubTypes(
    JsonSubTypes.Type(Author.Agent::class, name = "Agent"),
    JsonSubTypes.Type(Author.User::class, name = "User"),
  )
  sealed class Author {
    data class Agent(
      val name: String,
    ) : Author()

    data class User(
      val userId: UserId,
    ) : Author()
  }

  data class Creator(
    val conversationId: ConversationId,
    val userId: UserId,
    val text: String,
  )
}
