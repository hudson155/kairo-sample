package kairoSample.exception

import kairo.exception.ForbiddenException
import kairo.id.KairoId

public class NoAccessToLibraryCard(
  private val libraryCardId: KairoId,
) : ForbiddenException(
  message = "You do not have access to this library card.",
) {
  override val response: Map<String, Any>
    get() = super.response + buildMap {
      put("libraryCardId", libraryCardId)
    }
}
