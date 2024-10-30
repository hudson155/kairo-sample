package kairoSample.common

import java.time.Instant
import kairo.id.KairoId

internal abstract class KairoSampleModel {
  abstract val id: KairoId
  abstract val version: Long
  abstract val createdAt: Instant
  abstract val updatedAt: Instant
  abstract val deletedAt: Instant?

  val isDeleted: Boolean
    get() = deletedAt != null

  internal abstract class Creator {
    abstract val id: KairoId
  }

  internal abstract class Update
}
