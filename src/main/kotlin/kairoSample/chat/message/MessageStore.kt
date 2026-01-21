package kairoSample.chat.message

import kairo.coroutines.singleNullOrThrow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.deleteReturning
import org.jetbrains.exposed.v1.r2dbc.insertReturning
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import org.koin.core.annotation.Single
import osiris.element.element.Element
import kairoSample.chat.conversation.ConversationId
import kairoSample.chat.conversation.ConversationStore
import kairoSample.chat.conversation.exception.ConversationNotFound
import kairoSample.chat.message.exception.MessageNotFound

@Single
class MessageStore(
  conversationStore: Lazy<ConversationStore>,
  private val database: R2dbcDatabase,
) {
  private val conversationStore: ConversationStore by conversationStore

  suspend fun get(messageId: MessageId, forUpdate: Boolean = false): MessageModel? =
    suspendTransaction(db = database) {
      MessageTable
        .selectAll()
        .let { if (forUpdate) it.forUpdate() else it }
        .where { MessageTable.id eq messageId }
        .map(MessageModel::fromRow)
        .singleNullOrThrow()
    }

  suspend fun listByConversation(conversationId: ConversationId): List<MessageModel> =
    suspendTransaction(db = database) {
      MessageTable
        .selectAll()
        .where { MessageTable.conversationId eq conversationId }
        .orderBy(Pair(MessageTable.createdAt, SortOrder.DESC))
        .map(MessageModel::fromRow)
        .toList()
    }

  suspend fun create(
    creator: MessageModel.Creator,
    elements: List<Element>,
  ): MessageModel =
    suspendTransaction(db = database) {
      val conversationId = creator.conversationId
      val conversation = conversationStore.get(conversationId)
        ?: throw ConversationNotFound.unprocessable(conversationId)
      return@suspendTransaction MessageTable
        .insertReturning { statement ->
          statement[this.id] = MessageId.random()
          statement[this.userId] = conversation.userId
          statement[this.conversationId] = conversationId
          statement[this.author] = creator.author
          statement[this.raw] = creator.raw
          statement[this.elements] = elements
        }
        .map(MessageModel::fromRow)
        .single()
    }

  suspend fun delete(id: MessageId): MessageModel =
    suspendTransaction(db = database) {
      MessageTable
        .deleteReturning(where = { MessageTable.id eq id })
        .map(MessageModel::fromRow)
        .singleNullOrThrow()
        ?: throw MessageNotFound.unprocessable(id)
    }
}
