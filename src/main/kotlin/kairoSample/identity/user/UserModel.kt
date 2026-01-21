package kairoSample.identity.user

import kairo.optional.Required
import kotlin.time.Instant

data class UserModel(
  val id: UserId,
  val createdAt: Instant,
  val firstName: String?,
  val lastName: String?,
  val emailAddress: String,
) {
  data class Creator(
    val firstName: String?,
    val lastName: String?,
    val emailAddress: String,
  ) {
    companion object
  }

  data class Update(
    val firstName: Required<String>,
    val lastName: Required<String>,
  ) {
    constructor() : this(
      firstName = Required.Missing,
      lastName = Required.Missing,
    )

    fun hasUpdates(): Boolean =
      listOf(
        firstName,
        lastName,
      ).any { it.isSpecified }
  }

  companion object
}
