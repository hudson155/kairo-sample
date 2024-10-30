package kairoSample.entity.libraryCard

import com.google.inject.Inject
import kairo.id.KairoId
import kairo.id.KairoIdGenerator

internal class LibraryCardMapper @Inject constructor(
  idGenerator: KairoIdGenerator.Factory,
) {
  private val idGenerator: KairoIdGenerator = idGenerator.withPrefix("library_card")

  fun generateId(): KairoId =
    idGenerator.generate()

  fun map(model: LibraryCardModel): LibraryCardRep =
    LibraryCardRep(
      id = model.id,
      libraryMemberId = model.libraryMemberId,
    )

  fun map(creator: LibraryCardRep.Creator): LibraryCardModel.Creator =
    LibraryCardModel.Creator(
      id = generateId(),
      libraryMemberId = creator.libraryMemberId,
    )
}
