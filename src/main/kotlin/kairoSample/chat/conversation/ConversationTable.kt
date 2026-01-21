package kairoSample.chat.conversation

import kairoSample.identity.user.UserId
import kotlin.time.Instant
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp

object ConversationTable : Table("chat.conversation") {
  val id: Column<ConversationId> =
    text("id")
      .transform(::ConversationId, ConversationId::value)

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
      .index("ix__conversation__user_id")

  val agentName: Column<String> =
    text("agent_name")

  val processing: Column<Boolean> =
    bool("processing")
}

fun ConversationModel.Companion.fromRow(row: ResultRow): ConversationModel =
  ConversationModel(
    id = row[ConversationTable.id],
    createdAt = row[ConversationTable.createdAt],
    userId = row[ConversationTable.userId],
    agentName = row[ConversationTable.agentName],
    processing = row[ConversationTable.processing],
  )
