package kairoSample.library.libraryBook

import kotlin.time.Instant
import kotlinx.serialization.Serializable

@Serializable
data class LibraryBookRep(
  val id: LibraryBookId,
  val createdAt: Instant,
  val title: String?,
  val authors: List<String>,
  val isbn: String,
) {
  @Serializable
  data class Creator(
    val title: String?,
    val authors: List<String>,
    val isbn: String,
  )
}
