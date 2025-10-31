package kairoSample.libraryBook

import kairo.optional.Optional
import kairo.optional.Required
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable

@Serializable
internal data class LibraryBookRep(
  val id: LibraryBookId,
  val createdAt: Instant,
  val title: String?,
  val authors: List<String>,
  val isbn: String,
) {
  @Serializable
  internal data class Creator(
    val title: String?,
    val authors: List<String>,
    val isbn: String,
  )

  @Serializable
  internal data class Update(
    @EncodeDefault(EncodeDefault.Mode.NEVER) @Contextual
    val title: Optional<String> = Optional.Missing,
    @EncodeDefault(EncodeDefault.Mode.NEVER) @Contextual
    val authors: Required<List<String>> = Required.Missing,
    @EncodeDefault(EncodeDefault.Mode.NEVER) @Contextual
    val isbn: Required<String> = Required.Missing,
  )
}
