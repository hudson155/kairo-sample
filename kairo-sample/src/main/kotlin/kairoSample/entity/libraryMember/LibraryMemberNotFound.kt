package kairoSample.entity.libraryMember

import kairo.exception.NotFoundException
import kairo.id.KairoId

public class LibraryMemberNotFound(
  private val libraryMemberId: KairoId?,
) : NotFoundException("The library member does not exist.") {
  override val response: Map<String, Any>
    get() = super.response + buildMap {
      libraryMemberId?.let { put("libraryMemberId", it) }
    }
}
