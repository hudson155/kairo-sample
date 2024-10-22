package kairoSample.entity.libraryMember

import com.google.inject.Inject
import kairo.id.KairoId
import kairo.updater.Updater

internal class LibraryMemberService @Inject constructor(
  private val libraryMemberStore: LibraryMemberStore,
) {
  suspend fun get(id: KairoId): LibraryMemberModel? =
    libraryMemberStore.get(id)

  suspend fun getByEmailAddress(emailAddress: String): LibraryMemberModel? =
    libraryMemberStore.getByEmailAddress(emailAddress)

  suspend fun listAll(): List<LibraryMemberModel> =
    libraryMemberStore.listAll()

  suspend fun create(creator: LibraryMemberModel.Creator): LibraryMemberModel =
    libraryMemberStore.create(creator)

  suspend fun update(id: KairoId, updater: Updater<LibraryMemberModel.Update>): LibraryMemberModel =
    libraryMemberStore.update(id, updater)

  suspend fun delete(id: KairoId): LibraryMemberModel =
    libraryMemberStore.delete(id)
}
