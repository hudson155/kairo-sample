package kairoSample.entity.libraryCard

import kairo.dependencyInjection.getInstance
import kairo.exception.unprocessable
import kairo.id.KairoId
import kairo.rest.auth.Auth
import kairo.rest.auth.AuthProvider
import kairoSample.entity.libraryMember.libraryMember

internal suspend fun AuthProvider.libraryCard(libraryCardId: KairoId?): Auth.Result {
  val libraryCardService = injector.getInstance<LibraryCardService>()

  val libraryCard = libraryCardId?.let { libraryCardService.get(libraryCardId) }
    ?: return Auth.Result.Exception(unprocessable(LibraryCardNotFound(libraryCardId)))

  return libraryMember(libraryCard.libraryMemberId)
}
