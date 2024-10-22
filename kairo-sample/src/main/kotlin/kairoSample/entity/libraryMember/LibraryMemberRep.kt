package kairoSample.entity.libraryMember

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.Optional
import kairo.id.KairoId
import kairoSample.common.KairoSampleRep

public data class LibraryMemberRep(
  override val id: KairoId,
  val emailAddress: String,
  val firstName: String?,
  val lastName: String?,
) : KairoSampleRep() {
  public data class Creator(
    val emailAddress: String,
    val firstName: String?,
    val lastName: String?,
  ) : KairoSampleRep.Creator()

  @JsonInclude(value = JsonInclude.Include.NON_NULL)
  public data class Update(
    val emailAddress: String? = null,
    val firstName: Optional<String>? = null,
    val lastName: Optional<String>? = null,
  ) : KairoSampleRep.Update()
}
