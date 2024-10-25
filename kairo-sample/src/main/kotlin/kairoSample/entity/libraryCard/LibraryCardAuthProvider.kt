package kairoSample.entity.libraryCard

import com.google.inject.Inject
import kairo.exception.unprocessable
import kairo.id.KairoId
import kairo.rest.auth.Auth
import kairoSample.auth.libraryMember

internal class LibraryCardAuthProvider @Inject constructor(
  private val libraryCardService: LibraryCardService,
) {
  suspend fun Auth.libraryCard(libraryCardId: KairoId?): Auth.Result {
    val libraryCard = libraryCardId?.let { libraryCardService.get(libraryCardId) }
      ?: return Auth.Result.Exception(unprocessable(LibraryCardNotFound(libraryCardId)))

    return libraryMember(libraryCard.libraryMemberId)
  }
}
