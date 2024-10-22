package kairoSample.auth

import kairo.id.KairoId
import kairo.rest.auth.Auth
import kairo.rest.auth.JwtPrincipal
import kairo.rest.auth.overriddenBy
import kairo.rest.exception.MissingJwtClaim
import kairo.rest.exception.NoPrincipal
import kairoSample.exception.NoAccessToUserAccount

internal fun Auth.userAccount(userAccountId: KairoId): Auth.Result {
  overriddenBy(superuser()) { return@userAccount it }

  val principal = principal ?: return Auth.Result.Exception(NoPrincipal())
  val jwt: JwtPrincipal.() -> Auth.Result = principal@{
    val userAccountIdClaim = getClaim<KairoId>(JwtClaimName.userAccountId)
      ?: throw MissingJwtClaim(JwtClaimName.userAccountId)
    if (userAccountIdClaim != userAccountId) return@principal Auth.Result.Exception(NoAccessToUserAccount())
    return@principal Auth.Result.Success
  }

  return principal.principal(
    jwt = jwt,
  )
}
