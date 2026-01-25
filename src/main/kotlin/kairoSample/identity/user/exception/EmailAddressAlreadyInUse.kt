package kairoSample.identity.user.exception

import io.ktor.http.HttpStatusCode
import kairo.exception.LogicalFailure

data class EmailAddressAlreadyInUse(
  val emailAddress: String,
) : LogicalFailure("Email address already in use") {
  override val type: String = "EmailAddressAlreadyInUse"
  override val status: HttpStatusCode = HttpStatusCode.Conflict

  override fun MutableMap<String, Any?>.buildJson() {
    put("emailAddress", emailAddress)
  }
}
