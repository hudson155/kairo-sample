package kairoSample.library.libraryBook

import org.koin.core.annotation.Single

@Single
class LibraryBookService(
  private val libraryBookStore: LibraryBookStore,
) {
  fun get(id: LibraryBookId): LibraryBookModel? =
    libraryBookStore.get(id)

  fun listAll(): List<LibraryBookModel> =
    libraryBookStore.listAll()

  fun create(creator: LibraryBookModel.Creator): LibraryBookModel =
    libraryBookStore.create(creator)
}
