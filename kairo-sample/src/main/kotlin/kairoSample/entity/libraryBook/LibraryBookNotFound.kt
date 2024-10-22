package kairoSample.entity.libraryBook

import kairo.exception.NotFoundException
import kairo.id.KairoId

public class LibraryBookNotFound(
  private val libraryBookId: KairoId?,
) : NotFoundException("The library book does not exist.") {
  override val response: Map<String, Any>
    get() = super.response + buildMap {
      libraryBookId?.let { put("libraryBookId", it) }
    }
}
