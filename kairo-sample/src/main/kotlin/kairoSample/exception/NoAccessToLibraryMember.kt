package kairoSample.exception

import kairo.exception.ForbiddenException
import kairo.id.KairoId

public class NoAccessToLibraryMember(
  private val libraryMemberId: KairoId,
) : ForbiddenException(
  message = "You do not have access to this library member.",
) {
  override val response: Map<String, Any>
    get() = super.response + buildMap {
      put("libraryMemberId", libraryMemberId)
    }
}
