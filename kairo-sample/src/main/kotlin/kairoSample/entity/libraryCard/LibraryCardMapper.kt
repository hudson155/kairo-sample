package kairoSample.entity.libraryCard

import com.google.inject.Inject
import kairo.id.KairoIdGenerator

internal class LibraryCardMapper @Inject constructor(
  idGenerator: KairoIdGenerator.Factory,
) {
  private val idGenerator: KairoIdGenerator = idGenerator.withPrefix("library_card")

  internal fun map(model: LibraryCardModel): LibraryCardRep =
    LibraryCardRep(
      id = model.id,
      libraryMemberId = model.libraryMemberId,
    )

  internal fun map(creator: LibraryCardRep.Creator): LibraryCardModel.Creator =
    LibraryCardModel.Creator(
      id = idGenerator.generate(),
      libraryMemberId = creator.libraryMemberId,
    )
}
