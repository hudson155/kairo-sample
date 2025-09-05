package kairoSample.library.libraryBook

import kairoSample.testing.epoch
import kotlin.time.Instant

internal val LibraryBookId.Companion.zero: LibraryBookId
  get() = LibraryBookId("library_book_00000000")

internal fun LibraryBookModel.sanitized(): LibraryBookModel =
  copy(
    id = LibraryBookId.zero,
    createdAt = Instant.epoch,
  )

internal val LibraryBookModel.Creator.Companion.mereChristianity: LibraryBookModel.Creator
  get() =
    LibraryBookModel.Creator(
      title = "Mere Christianity",
      authors = listOf("C. S. Lewis"),
      isbn = "978-0060652920",
    )

internal val LibraryBookModel.Companion.mereChristianity: LibraryBookModel
  get() =
    LibraryBookModel(
      id = LibraryBookId.zero,
      createdAt = Instant.epoch,
      title = "Mere Christianity",
      authors = listOf("C. S. Lewis"),
      isbn = "978-0060652920",
    )

internal val LibraryBookModel.Creator.Companion.theMeaningOfMarriage: LibraryBookModel.Creator
  get() =
    LibraryBookModel.Creator(
      title = "The Meaning of Marriage: Facing the Complexities of Commitment with the Wisdom of God",
      authors = listOf("Timothy Keller", "Kathy Keller"),
      isbn = "978-1594631870",
    )

internal val LibraryBookModel.Companion.theMeaningOfMarriage: LibraryBookModel
  get() =
    LibraryBookModel(
      id = LibraryBookId.zero,
      createdAt = Instant.epoch,
      title = "The Meaning of Marriage: Facing the Complexities of Commitment with the Wisdom of God",
      authors = listOf("Timothy Keller", "Kathy Keller"),
      isbn = "978-1594631870",
    )
