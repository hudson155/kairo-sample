package kairoSample.auth

import kairo.exception.unprocessable
import kairo.id.KairoId
import kairo.rest.auth.Auth
import kairo.rest.auth.overriddenBy
import kairo.rest.exception.MissingJwtClaim
import kairo.rest.exception.NoPrincipal
import kairoSample.entity.libraryMember.LibraryMemberNotFound
import kairoSample.exception.NoAccessToLibraryMember

internal fun Auth.libraryMember(libraryMemberId: KairoId?): Auth.Result {
  libraryMemberId ?: return Auth.Result.Exception(unprocessable(LibraryMemberNotFound(null)))

  overriddenBy(superuser()) { return@libraryMember it }

  val principal = principal ?: return Auth.Result.Exception(NoPrincipal())

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
