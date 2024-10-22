package kairoSample.auth

import kairo.rest.auth.Auth
import kairo.rest.auth.JwtPrincipal
import kairo.rest.exception.MissingJwtClaim
import kairo.rest.exception.NoPrincipal
import kairoSample.exception.NotSuperuser

internal fun Auth.superuser(): Auth.Result {
  val principal = principal ?: return Auth.Result.Exception(NoPrincipal())
  val jwt: JwtPrincipal.() -> Auth.Result = principal@{
    val platformRolesClaim = getClaim<List<PlatformRole>>(JwtClaimName.platformRoles)
      ?: throw MissingJwtClaim(JwtClaimName.platformRoles)
    if (PlatformRole.Superuser !in platformRolesClaim) return@principal Auth.Result.Exception(NotSuperuser())
    return@principal Auth.Result.Success
  }
  return principal.principal(
    jwt = jwt,
  )
}
