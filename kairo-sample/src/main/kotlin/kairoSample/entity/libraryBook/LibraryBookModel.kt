package kairoSample.entity.libraryBook

import java.time.Instant
import kairo.id.KairoId
import kairoSample.common.KairoSampleModel

internal data class LibraryBookModel(
  override val id: KairoId,
  override val version: Long,
  override val createdAt: Instant,
  override val updatedAt: Instant,
  override val deletedAt: Instant?,
  val title: String,
  val author: String?,
  val isbn: String,
) : KairoSampleModel() {
  internal data class Creator(
    override val id: KairoId,
    val title: String,
    val author: String?,
    val isbn: String,
  ) : KairoSampleModel.Creator()

  internal data class Update(
    val title: String,
    val author: String?,
  ) : KairoSampleModel.Update() {
    constructor(model: LibraryBookModel) : this(
      title = model.title,
      author = model.author,
    )
  }
}
