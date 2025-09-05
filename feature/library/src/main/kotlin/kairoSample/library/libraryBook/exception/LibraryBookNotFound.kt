package kairoSample.library.libraryBook.exception

import kairo.exception.LogicalFailure
import kairoSample.library.libraryBook.LibraryBookId
import kotlinx.serialization.Serializable

@Serializable
internal data class LibraryBookNotFound(
  val libraryBookId: LibraryBookId?,
) : LogicalFailure.Properties()
