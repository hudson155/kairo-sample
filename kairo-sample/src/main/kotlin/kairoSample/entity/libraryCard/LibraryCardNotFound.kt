package kairoSample.entity.libraryCard

import kairo.exception.NotFoundException
import kairo.id.KairoId

public class LibraryCardNotFound(
  private val libraryCardId: KairoId?,
) : NotFoundException("The library card does not exist.") {
  override val response: Map<String, Any>
    get() = super.response + buildMap {
      libraryCardId?.let { put("libraryCardId", it) }
    }
}
