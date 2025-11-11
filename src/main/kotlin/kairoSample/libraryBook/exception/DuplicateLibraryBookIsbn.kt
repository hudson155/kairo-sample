package kairoSample.libraryBook.exception

import io.ktor.http.HttpStatusCode
import kairo.exception.LogicalFailure
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.JsonPrimitive

data class DuplicateLibraryBookIsbn(
  val isbn: String,
) : LogicalFailure("Duplicate library book ISBN") {
  override val type: String = "DuplicateLibraryBookIsbn"
  override val status: HttpStatusCode = HttpStatusCode.Conflict

  override fun JsonObjectBuilder.buildJson() {
    put("isbn", JsonPrimitive(isbn))
  }
}
