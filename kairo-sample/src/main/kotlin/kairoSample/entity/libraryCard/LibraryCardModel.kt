package kairoSample.entity.libraryCard

import java.time.Instant
import kairo.id.KairoId
import kairoSample.common.KairoSampleModel

internal data class LibraryCardModel(
  override val id: KairoId,
  override val version: Long,
  override val createdAt: Instant,
  override val updatedAt: Instant,
  override val deletedAt: Instant?,
  val libraryMemberId: KairoId,
) : KairoSampleModel() {
  internal data class Creator(
    override val id: KairoId,
    val libraryMemberId: KairoId,
  ) : KairoSampleModel.Creator()
}
