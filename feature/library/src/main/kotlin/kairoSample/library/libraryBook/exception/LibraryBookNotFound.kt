package kairoSample.library.libraryBook.exception

import io.ktor.http.HttpStatusCode
import kairo.exception.LogicalFailure
import kairoSample.library.libraryBook.LibraryBookId
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.encodeToJsonElement

internal data class LibraryBookNotFound(
  val libraryBookId: LibraryBookId?,
) : LogicalFailure() {
  override val type: String = "LibraryBookNotFound"
  override val status: HttpStatusCode = HttpStatusCode.NotFound
  override val title: String = "Library book not found"

  override fun JsonObjectBuilder.buildJson() {
    put("libraryBookId", Json.encodeToJsonElement(libraryBookId))
  }
}
