package kairoSample.chat.conversation

import kairo.datetime.epoch
import kotlin.time.Instant
import kairoSample.identity.user.UserId

val ConversationId.Companion.zero: ConversationId
  get() = ConversationId("convo_00000000")

fun ConversationModel.sanitized(): ConversationModel =
  copy(
    id = ConversationId.zero,
    createdAt = Instant.epoch,
  )

fun ConversationModel.Creator.Companion.fixture(
  userId: UserId,
): ConversationModel.Creator =
  ConversationModel.Creator(
    userId = userId,
    agentName = "test",
    processing = false,
  )

fun ConversationModel.Companion.fixture(
  userId: UserId,
): ConversationModel =
  ConversationModel(
    id = ConversationId.zero,
    createdAt = Instant.epoch,
    userId = userId,
    agentName = "test",
    processing = false,
  )
