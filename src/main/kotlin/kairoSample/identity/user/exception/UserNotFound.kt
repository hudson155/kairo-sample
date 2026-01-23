package kairoSample.identity.user.exception

import io.ktor.http.HttpStatusCode
import kairo.exception.LogicalFailure
import kairoSample.identity.user.UserId

class UserNotFound private constructor(
  override val status: HttpStatusCode,
  val userId: UserId?,
) : LogicalFailure("User not found") {
  override val type: String = "UserNotFound"

  constructor(userId: UserId?) : this(
    status = HttpStatusCode.NotFound,
    userId = userId,
  )

  override fun MutableMap<String, Any?>.buildJson() {
    put("userId", userId)
  }

  companion object {
    fun unprocessable(userId: UserId): UserNotFound =
      UserNotFound(
        status = HttpStatusCode.UnprocessableEntity,
        userId = userId,
      )
  }
}
