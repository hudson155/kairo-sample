package kairoSample.chat.message

import dev.langchain4j.data.message.UserMessage
import kairo.datetime.epoch
import kairoSample.chat.conversation.ConversationId
import kairoSample.identity.user.UserId
import kotlin.time.Instant
import osiris.element.element.ParagraphElement

val MessageId.Companion.zero: MessageId
  get() = MessageId("msg_00000000")

fun MessageModel.sanitized(): MessageModel =
  copy(
    id = MessageId.zero,
    createdAt = Instant.epoch,
  )

fun MessageModel.Creator.Companion.fixture(
  userId: UserId,
  conversationId: ConversationId,
): MessageModel.Creator =
  MessageModel.Creator(
    conversationId = conversationId,
    author = MessageRep.Author.User(
      userId = userId,
    ),
    raw = UserMessage.from("Hello!"),
  )

fun MessageModel.Companion.fixture(
  userId: UserId,
  conversationId: ConversationId,
): MessageModel =
  MessageModel(
    id = MessageId.zero,
    createdAt = Instant.epoch,
    userId = userId,
    conversationId = conversationId,
    author = MessageRep.Author.User(
      userId = userId,
    ),
    raw = UserMessage.from("Hello!"),
    elements = listOf(ParagraphElement.text("Hello!")),
  )
