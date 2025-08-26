package kairoSample.library.libraryBook

import kotlin.time.Instant

data class LibraryBookModel(
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
  )
}
