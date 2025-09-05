package kairoSample.library.libraryBook.exception

import kairo.exception.LogicalFailure
import kotlinx.serialization.Serializable

@Serializable
internal data class DuplicateLibraryBookIsbn(
  val isbn: String,
) : LogicalFailure.Properties()
