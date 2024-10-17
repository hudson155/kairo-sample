package kairoSample.auth

import kairo.rest.auth.Auth
import kairo.rest.auth.JwtPrincipal
import kairo.rest.auth.Principal
import kairo.rest.exception.NoAuthorization
import kairoSample.exception.NotSuperuser

internal fun Auth.superuser(): Auth.Result {
  val principal = principal ?: return Auth.Result.Exception(NoAuthorization())
  return principal.superuser()
}

internal fun Principal.superuser(): Auth.Result {
  val jwt: JwtPrincipal.() -> Auth.Result = principal@{
    val platformRolesClaim = getClaim<List<PlatformRole>>(JwtClaimName.platformRoles).orEmpty()
    if (PlatformRole.Superuser !in platformRolesClaim) return@principal Auth.Result.Exception(NotSuperuser())
    return@principal Auth.Result.Success
  }

  return principal(
    jwt = jwt,
  )
}
