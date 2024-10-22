package kairoSample.entity.libraryCard

import com.google.inject.Inject
import kairo.id.KairoId

internal class LibraryCardService @Inject constructor(
  private val libraryCardStore: LibraryCardStore,
) {
  suspend fun get(id: KairoId): LibraryCardModel? =
    libraryCardStore.get(id)

  suspend fun listAll(): List<LibraryCardModel> =
    libraryCardStore.listAll()

  suspend fun listByLibraryMember(libraryMemberId: KairoId): List<LibraryCardModel> =
    libraryCardStore.listByLibraryMember(libraryMemberId)

  suspend fun create(creator: LibraryCardModel.Creator): LibraryCardModel =
    libraryCardStore.create(creator)

  suspend fun delete(id: KairoId): LibraryCardModel =
    libraryCardStore.delete(id)
}
