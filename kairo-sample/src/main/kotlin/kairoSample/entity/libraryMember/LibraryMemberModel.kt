package kairoSample.entity.libraryMember

import java.time.Instant
import kairo.id.KairoId
import kairoSample.common.KairoSampleModel

internal data class LibraryMemberModel(
  override val id: KairoId,
  override val version: Long,
  override val createdAt: Instant,
  override val updatedAt: Instant,
  override val deletedAt: Instant?,
  val emailAddress: String,
  val firstName: String?,
  val lastName: String?,
) : KairoSampleModel() {
  internal data class Creator(
    override val id: KairoId,
    val emailAddress: String,
    val firstName: String?,
    val lastName: String?,
  ) : KairoSampleModel.Creator()

  internal data class Update(
    val emailAddress: String,
    val firstName: String?,
    val lastName: String?,
  ) : KairoSampleModel.Update() {
    constructor(model: LibraryMemberModel) : this(
      emailAddress = model.emailAddress,
      firstName = model.firstName,
      lastName = model.lastName,
    )
  }
}
