package kairoSample.identity.user

import kairo.coroutines.singleNullOrThrow
import kairo.optional.ifSpecified
import kairoSample.identity.user.exception.UserNotFound
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
import org.jetbrains.exposed.v1.r2dbc.updateReturning
import org.koin.core.annotation.Single

@Single
class UserStore(
  private val database: R2dbcDatabase,
) {
  suspend fun get(id: UserId): UserModel? =
    suspendTransaction(db = database) {
      UserTable
        .selectAll()
        .where { UserTable.id eq id }
        .map(UserModel::fromRow)
        .singleNullOrThrow()
    }

  suspend fun listAll(): List<UserModel> =
    suspendTransaction(db = database) {
      UserTable
        .selectAll()
        .orderBy(Pair(UserTable.createdAt, SortOrder.DESC))
        .map(UserModel::fromRow)
        .toList()
    }

  suspend fun create(creator: UserModel.Creator): UserModel =
    suspendTransaction(db = database) {
      UserTable
        .insertReturning { statement ->
          statement[this.id] = UserId.random()
          statement[this.firstName] = creator.firstName
          statement[this.lastName] = creator.lastName
          statement[this.emailAddress] = creator.emailAddress
        }
        .map(UserModel::fromRow)
        .single()
    }

  suspend fun update(id: UserId, update: UserModel.Update): UserModel {
    if (!update.hasUpdates()) return get(id) ?: throw UserNotFound.unprocessable(id)
    return suspendTransaction(db = database) {
      UserTable
        .updateReturning(where = { UserTable.id eq id }) { statement ->
          update.firstName.ifSpecified { statement[this.firstName] = it }
          update.lastName.ifSpecified { statement[this.lastName] = it }
        }
        .map(UserModel::fromRow)
        .singleNullOrThrow()
        ?: throw UserNotFound.unprocessable(id)
    }
  }

  suspend fun delete(id: UserId): UserModel =
    suspendTransaction(db = database) {
      UserTable
        .deleteReturning(where = { UserTable.id eq id })
        .map(UserModel::fromRow)
        .singleNullOrThrow()
        ?: throw UserNotFound.unprocessable(id)
    }
}
