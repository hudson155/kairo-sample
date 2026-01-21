package kairoSample.identity.user

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import kairo.rest.HasRouting
import kairo.rest.route
import kairoSample.identity.user.exception.UserNotFound
import org.koin.core.annotation.Single

@Single
class UserHandler(
  private val userMapper: UserMapper,
  private val userService: UserService,
) : HasRouting {
  @Suppress("LongMethod")
  override fun Application.routing() {
    routing {
      route(UserApi.Get::class) {
        handle {
          val userId = endpoint.userId
          val user = userService.get(userId)
            ?: throw UserNotFound(userId)
          userMapper.rep(user)
        }
      }

      route(UserApi.ListAll::class) {
        handle {
          val users = userService.listAll()
          users.map { userMapper.rep(it) }
        }
      }

      route(UserApi.Create::class) {
        handle {
          val user = userService.create(
            creator = userMapper.creator(endpoint.body),
          )
          userMapper.rep(user)
        }
      }

      route(UserApi.Update::class) {
        handle {
          val user = userService.update(
            id = endpoint.userId,
            update = userMapper.update(endpoint.body),
          )
          userMapper.rep(user)
        }
      }

      route(UserApi.Delete::class) {
        handle {
          val user = userService.delete(endpoint.userId)
          userMapper.rep(user)
        }
      }
    }
  }
}
