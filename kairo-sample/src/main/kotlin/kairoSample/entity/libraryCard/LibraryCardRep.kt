package kairoSample.entity.libraryCard

import kairo.id.KairoId
import kairoSample.common.KairoSampleRep

public data class LibraryCardRep(
  override val id: KairoId,
  val libraryMemberId: KairoId,
) : KairoSampleRep() {
  public data class Creator(
    val libraryMemberId: KairoId,
  ) : KairoSampleRep.Creator()
}
