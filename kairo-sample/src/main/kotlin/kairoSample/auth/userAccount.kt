package kairoSample.auth

import kairo.id.KairoId
import kairo.rest.auth.Auth
import kairo.rest.auth.JwtPrincipal
import kairo.rest.auth.Principal
import kairo.rest.exception.MissingJwtClaim
import kairo.rest.exception.NoAuthorization
import kairoSample.exception.NoAccessToUserAccount

internal fun Auth.userAccount(userAccountId: KairoId): Auth.Result {
  val principal = principal ?: return Auth.Result.Exception(NoAuthorization())
  return principal.userAccount(userAccountId)
}

internal fun Principal.userAccount(userAccountId: KairoId): Auth.Result {
  val jwt: JwtPrincipal.() -> Auth.Result = principal@{
    val userAccountIdClaim = getClaim<KairoId>(JwtClaimName.userAccountId)
      ?: return@principal Auth.Result.Exception(MissingJwtClaim(JwtClaimName.userAccountId))
    if (userAccountIdClaim != userAccountId) return@principal Auth.Result.Exception(NoAccessToUserAccount())
    return@principal Auth.Result.Success
  }

  return principal(
    jwt = jwt,
  )
}
