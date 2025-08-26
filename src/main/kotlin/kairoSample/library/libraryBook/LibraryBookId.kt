package kairoSample.library.libraryBook

import kairo.id.Id
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class LibraryBookId(override val value: String) : Id {
  init {
    require(regex.matches(value)) { "Malformed library book ID (value=$value). " }
  }

  companion object {
    val regex: Regex = Id.regex(prefix = Regex("library_book"))
  }
}
