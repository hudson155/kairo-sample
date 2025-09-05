package kairoSample.library.libraryBook.exception

import io.ktor.http.HttpStatusCode
import kairo.exception.LogicalFailure
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.JsonPrimitive

internal data class DuplicateLibraryBookIsbn(
  val isbn: String,
) : LogicalFailure() {
  override val type: String = "DuplicateLibraryBookIsbn"
  override val status: HttpStatusCode = HttpStatusCode.Conflict
  override val title: String = "Duplicate library book ISBN"

  override fun JsonObjectBuilder.buildJson() {
    put("isbn", JsonPrimitive(isbn))
  }
}
