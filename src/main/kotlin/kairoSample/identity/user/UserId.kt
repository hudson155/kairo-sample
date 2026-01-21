package kairoSample.identity.user

import kairo.id.Id

@JvmInline
value class UserId(override val value: String) : Id {
  init {
    require(regex.matches(value)) { "Malformed user ID (value=$value). " }
  }

  companion object : Id.Companion<UserId>() {
    val regex: Regex = regex(prefix = Regex("user"))

    override fun create(payload: String): UserId =
      UserId("user_$payload")
  }
}
