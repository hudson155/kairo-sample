package kairoSample.libraryBook

import kairo.id.Id
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class LibraryBookId(val value: Id) {
  init {
    require(value.toString().matches(regex))
  }

  override fun toString(): String =
    value.toString()

  companion object {
    val regex: Regex = Id.regex(prefix = Regex("library_book"))
  }
}
