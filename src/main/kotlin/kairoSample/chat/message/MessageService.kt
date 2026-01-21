package kairoSample.chat.message

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kairoSample.chat.conversation.ConversationId
import kairoSample.chat.message.exception.MessageNotFound
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.r2dbc.updateReturning
import org.koin.core.annotation.Single

private val logger: KLogger = KotlinLogging.logger {}

@Single
class MessageService(
  private val database: R2dbcDatabase,
  private val elementExtractor: ElementExtractor,
  private val messageStore: MessageStore,
) {
  suspend fun get(messageId: MessageId): MessageModel? =
    messageStore.get(messageId)

  suspend fun listByConversation(conversationId: ConversationId): List<MessageModel> =
    messageStore.listByConversation(conversationId)

  suspend fun create(creator: MessageModel.Creator): MessageModel {
    logger.info { "Creating message (creator=$creator)." }
    return messageStore.create(
      creator = creator,
      elements = elementExtractor.extract(creator.author, creator.raw),
    )
  }

  // TODO: This method is missing tests.
  suspend fun recomputeElements(id: MessageId): MessageModel {
    logger.info { "Recomputing elements for message (id=$id)." }
    return suspendTransaction(db = database) {
      val message = messageStore.get(id, forUpdate = true) ?: throw MessageNotFound.unprocessable(id)
      val elements = elementExtractor.extract(message.author, message.raw)
      return@suspendTransaction MessageTable
        .updateReturning(where = { MessageTable.id eq id }) { statement ->
          statement[this.elements] = elements
        }
        .map(MessageModel::fromRow)
        .single()
    }
  }

  suspend fun delete(id: MessageId): MessageModel {
    logger.info { "Deleting message (id=$id)." }
    return messageStore.delete(id)
  }
}
