package kairoSample.library.libraryBook

import kotlin.time.Instant
import org.jetbrains.exposed.v1.core.ResultRow

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
  )

  companion object
}

internal fun LibraryBookModel.Companion.fromRow(row: ResultRow): LibraryBookModel =
  LibraryBookModel(
    id = row[LibraryBookTable.id],
    createdAt = row[LibraryBookTable.createdAt],
    title = row[LibraryBookTable.title],
    authors = row[LibraryBookTable.authors],
    isbn = row[LibraryBookTable.isbn],
  )
