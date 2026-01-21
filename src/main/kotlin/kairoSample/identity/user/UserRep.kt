package kairoSample.identity.user

import com.fasterxml.jackson.annotation.JsonInclude
import kairo.optional.Required
import kotlin.time.Instant

data class UserRep(
  val id: UserId,
  val createdAt: Instant,
  val firstName: String?,
  val lastName: String?,
  val emailAddress: String,
) {
  data class Creator(
    val firstName: String? = null,
    val lastName: String? = null,
    val emailAddress: String,
  )

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  data class Update(
    val firstName: Required<String> = Required.Missing,
    val lastName: Required<String> = Required.Missing,
  )
}
