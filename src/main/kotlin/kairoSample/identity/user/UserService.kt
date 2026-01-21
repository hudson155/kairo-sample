package kairoSample.identity.user

import com.stytch.java.consumer.models.users.CreateRequest as UserCreateRequest
import com.stytch.java.consumer.models.users.DeleteRequest as UserDeleteRequest
import com.stytch.java.consumer.models.users.Name as UserName
import com.stytch.java.consumer.models.users.UpdateRequest as UserUpdateRequest
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kairo.stytch.Stytch
import kairo.stytch.get
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import org.koin.core.annotation.Single

private val logger: KLogger = KotlinLogging.logger {}

@Single
class UserService(
  private val database: R2dbcDatabase,
  private val stytch: Stytch,
  private val userStore: UserStore,
) {
  suspend fun get(id: UserId): UserModel? =
    userStore.get(id)

  suspend fun listAll(): List<UserModel> =
    userStore.listAll()

  suspend fun create(creator: UserModel.Creator): UserModel {
    logger.info { "Creating user (creator=$creator)." }
    return suspendTransaction(database) {
      val user = userStore.create(creator)
      createStytchUser(user)
      return@suspendTransaction user
    }
  }

  private suspend fun createStytchUser(user: UserModel) {
    stytch.users.create(
      UserCreateRequest(
        email = user.emailAddress,
        name = UserName(
          firstName = user.firstName,
          lastName = user.lastName,
        ),
        externalId = user.id.value,
      ),
    ).get()
  }

  suspend fun update(id: UserId, update: UserModel.Update): UserModel {
    logger.info { "Updating user (id=$id, update=$update)." }
    return suspendTransaction(database) {
      val user = userStore.update(id, update)
      updateStytchUser(user)
      return@suspendTransaction user
    }
  }

  private suspend fun updateStytchUser(user: UserModel) {
    stytch.users.update(
      UserUpdateRequest(
        userId = user.id.value,
        name = UserName(
          firstName = user.firstName,
          lastName = user.lastName,
        ),
      ),
    ).get()
  }

  suspend fun delete(id: UserId): UserModel {
    logger.info { "Deleting user (id=$id)." }
    return suspendTransaction(database) {
      val user = userStore.delete(id)
      deleteStytchUser(user)
      return@suspendTransaction user
    }
  }

  private suspend fun deleteStytchUser(user: UserModel) {
    stytch.users.delete(
      UserDeleteRequest(
        userId = user.id.value,
      ),
    ).get()
  }
}
