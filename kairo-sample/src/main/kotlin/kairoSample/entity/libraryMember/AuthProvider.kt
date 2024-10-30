package kairoSample.entity.libraryMember

import kairo.dependencyInjection.getInstance
import kairo.exception.unprocessable
import kairo.id.KairoId
import kairo.rest.auth.Auth
import kairo.rest.auth.AuthProvider
import kairo.rest.auth.overriddenBy
import kairo.rest.exception.MissingJwtClaim
import kairo.rest.exception.NoPrincipal
import kairoSample.auth.JwtClaimName
import kairoSample.auth.principal
import kairoSample.auth.superuser
import kairoSample.exception.NoAccessToLibraryMember

internal suspend fun AuthProvider.libraryMember(libraryMemberId: KairoId?): Auth.Result {
  val libraryMemberService = injector.getInstance<LibraryMemberService>()

  libraryMemberId?.let { libraryMemberService.get(libraryMemberId) }
    ?: return Auth.Result.Exception(unprocessable(LibraryMemberNotFound(libraryMemberId)))

  overriddenBy(superuser()) { return@libraryMember it }

  val principal = auth.principal ?: return Auth.Result.Exception(NoPrincipal())

  return principal.principal(
    jwt = jwt@{
      val libraryMemberIdClaim = getClaim<KairoId>(JwtClaimName.libraryMemberId)
        ?: throw MissingJwtClaim(JwtClaimName.libraryMemberId)
      if (libraryMemberIdClaim != libraryMemberId) {
        return@jwt Auth.Result.Exception(NoAccessToLibraryMember(libraryMemberId))
      }
      return@jwt Auth.Result.Success
    },
  )
}
