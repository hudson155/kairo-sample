package kairoSample.chat.message

import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.message.ChatMessageDeserializer
import dev.langchain4j.data.message.ChatMessageSerializer
import kairoSample.chat.conversation.ConversationId
import kairoSample.chat.conversation.ConversationTable
import kairoSample.identity.user.UserId
import kairoSample.jsonb
import kotlin.time.Instant
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp
import org.jetbrains.exposed.v1.json.jsonb
import osiris.element.element.Element

object MessageTable : Table("chat.message") {
  val id: Column<MessageId> =
    text("id")
      .transform(::MessageId, MessageId::value)

  override val primaryKey: PrimaryKey = PrimaryKey(id)

  val createdAt: Column<Instant> =
    timestamp("created_at")
      .defaultExpression(CurrentTimestamp)

  val version: Column<Long> =
    long("version")
      .default(0)

  val updatedAt: Column<Instant> =
    timestamp("updated_at")
      .defaultExpression(CurrentTimestamp)

  val userId: Column<UserId> =
    text("user_id")
      .transform(::UserId, UserId::value)
      .index("ix__message__user_id")

  val conversationId: Column<ConversationId> =
    text("conversation_id")
      .transform(::ConversationId, ConversationId::value)
      .references(
        ref = ConversationTable.id,
        onDelete = ReferenceOption.CASCADE,
        fkName = "fk__message__conversation_id",
      )
      .index("ix__message__conversation_id")

  val author: Column<MessageRep.Author> =
    jsonb("author")

  val raw: Column<ChatMessage> =
    jsonb(
      name = "raw",
      serialize = ChatMessageSerializer::messageToJson,
      deserialize = ChatMessageDeserializer::messageFromJson,
    )

  val elements: Column<List<Element>> =
    jsonb("elements")
}

fun MessageModel.Companion.fromRow(row: ResultRow): MessageModel =
  MessageModel(
    id = row[MessageTable.id],
    createdAt = row[MessageTable.createdAt],
    userId = row[MessageTable.userId],
    conversationId = row[MessageTable.conversationId],
    author = row[MessageTable.author],
    raw = row[MessageTable.raw],
    elements = row[MessageTable.elements],
  )
