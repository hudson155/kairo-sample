package kairoSample.chat.conversation

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
import kairoSample.chat.conversation.exception.ConversationNotFound
import kairoSample.identity.user.UserId

@Single
class ConversationStore(
  private val database: R2dbcDatabase,
) {
  suspend fun get(id: ConversationId): ConversationModel? =
    suspendTransaction(db = database) {
      ConversationTable
        .selectAll()
        .where { ConversationTable.id eq id }
        .map(ConversationModel::fromRow)
        .singleNullOrThrow()
    }

  suspend fun listByUser(userId: UserId): List<ConversationModel> =
    suspendTransaction(db = database) {
      ConversationTable
        .selectAll()
        .where { ConversationTable.userId eq userId }
        .orderBy(Pair(ConversationTable.createdAt, SortOrder.DESC))
        .map(ConversationModel::fromRow)
        .toList()
    }

  suspend fun create(creator: ConversationModel.Creator): ConversationModel =
    suspendTransaction(db = database) {
      ConversationTable
        .insertReturning { statement ->
          statement[this.id] = ConversationId.random()
          statement[this.userId] = creator.userId
          statement[this.agentName] = creator.agentName
          statement[this.processing] = creator.processing
        }
        .map(ConversationModel::fromRow)
        .single()
    }

  suspend fun delete(id: ConversationId): ConversationModel =
    suspendTransaction(db = database) {
      ConversationTable
        .deleteReturning(where = { ConversationTable.id eq id })
        .map(ConversationModel::fromRow)
        .singleNullOrThrow()
        ?: throw ConversationNotFound.unprocessable(id)
    }
}
