package kairoSample.library.libraryBook.exception

import io.ktor.http.HttpStatusCode
import kairo.exception.LogicalFailure
import kairoSample.library.libraryBook.LibraryBookId
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.encodeToJsonElement

internal data class LibraryBookNotFound private constructor(
  override val status: HttpStatusCode,
  val libraryBookId: LibraryBookId?,
) : LogicalFailure() {
  override val type: String = "LibraryBookNotFound"
  override val title: String = "Library book not found"

  internal constructor(libraryBookId: LibraryBookId?) : this(
    status = HttpStatusCode.NotFound,
    libraryBookId = libraryBookId,
  )

  override fun JsonObjectBuilder.buildJson() {
    libraryBookId?.let { put("libraryBookId", Json.encodeToJsonElement(it)) }
  }

  internal companion object {
    fun unprocessable(libraryBookId: LibraryBookId): LibraryBookNotFound =
      LibraryBookNotFound(
        status = HttpStatusCode.UnprocessableEntity,
        libraryBookId = libraryBookId,
      )
  }
}
