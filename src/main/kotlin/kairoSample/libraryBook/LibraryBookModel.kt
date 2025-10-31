package kairoSample.libraryBook

import kairo.optional.Optional
import kairo.optional.Required
import kotlin.time.Instant

internal data class LibraryBookModel(
  val id: LibraryBookId,
  val createdAt: Instant,
  val title: String?,
  val authors: List<String>,
  val isbn: String,
) {
  data class Creator(
    val title: String?,
    val authors: List<String>,
    val isbn: String,
  ) {
    internal companion object
  }

  data class Update(
    val title: Optional<String>,
    val authors: Required<List<String>>,
    val isbn: Required<String>,
  ) {
    constructor() : this(
      title = Optional.Missing,
      authors = Required.Missing,
      isbn = Required.Missing,
    )

    fun hasUpdates(): Boolean =
      listOf(title, authors, isbn).any { it.isSpecified }
  }

  internal companion object
}
