package kairoSample.entity.userAccount

import kairo.id.KairoId
import kairoSample.common.KairoSampleRep

public data class UserAccountRep(
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
}
