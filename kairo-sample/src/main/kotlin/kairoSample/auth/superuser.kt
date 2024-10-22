package kairoSample.auth

import kairo.rest.auth.Auth
import kairo.rest.exception.MissingJwtClaim
import kairo.rest.exception.NoPrincipal
import kairoSample.exception.NotSuperuser

internal fun Auth.superuser(): Auth.Result {
  val principal = principal ?: return Auth.Result.Exception(NoPrincipal())

  return principal.principal(
    jwt = jwt@{
      val platformRolesClaim = getClaim<List<PlatformRole>>(JwtClaimName.platformRoles)
        ?: throw MissingJwtClaim(JwtClaimName.platformRoles)
      if (PlatformRole.Superuser !in platformRolesClaim) {
        return@jwt Auth.Result.Exception(NotSuperuser())
      }
      return@jwt Auth.Result.Success
    },
  )
}
