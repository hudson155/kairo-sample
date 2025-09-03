package kairoSample.library.libraryBook

import org.koin.core.annotation.Single

@Single
internal class LibraryBookService(
  private val libraryBookStore: LibraryBookStore,
) {
  suspend fun get(id: LibraryBookId): LibraryBookModel? =
    libraryBookStore.get(id)

  suspend fun listAll(): List<LibraryBookModel> =
    libraryBookStore.listAll()

  suspend fun create(creator: LibraryBookModel.Creator): LibraryBookModel =
    libraryBookStore.create(creator)
}
