package kairoSample.entity.libraryBook

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.Optional
import kairo.id.KairoId
import kairoSample.common.KairoSampleRep

public data class LibraryBookRep(
  override val id: KairoId,
  val title: String,
  val author: String?,
  val isbn: String,
) : KairoSampleRep() {
  public data class Creator(
    val title: String,
    val author: String?,
    val isbn: String,
  ) : KairoSampleRep.Creator()

  @JsonInclude(value = JsonInclude.Include.NON_NULL)
  public data class Update(
    val title: String? = null,
    val author: Optional<String>? = null,
  ) : KairoSampleRep.Update()
}
