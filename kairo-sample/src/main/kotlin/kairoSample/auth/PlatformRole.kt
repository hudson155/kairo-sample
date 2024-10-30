package kairoSample.auth

import kairo.rest.auth.Auth
import kairo.rest.auth.AuthProvider
import kairo.rest.exception.MissingJwtClaim
import kairo.rest.exception.NoPrincipal
import kairoSample.exception.NotSuperuser

internal enum class PlatformRole { Superuser }

internal fun AuthProvider.superuser(): Auth.Result {
  val principal = auth.principal ?: return Auth.Result.Exception(NoPrincipal())

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
