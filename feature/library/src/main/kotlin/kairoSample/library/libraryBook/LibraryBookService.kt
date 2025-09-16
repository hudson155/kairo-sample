package kairoSample.library.libraryBook

import org.koin.core.annotation.Single

@Single
internal class LibraryBookService(
  private val libraryBookStore: LibraryBookStore,
) {
  fun get(id: LibraryBookId): LibraryBookModel? =
    libraryBookStore.get(id)

  fun getByIsbn(isbn: String): LibraryBookModel? =
    libraryBookStore.getByIsbn(isbn)

  fun listAll(): List<LibraryBookModel> =
    libraryBookStore.listAll()

  fun create(creator: LibraryBookModel.Creator): LibraryBookModel =
    libraryBookStore.create(creator)

  fun update(id: LibraryBookId, update: LibraryBookModel.Update): LibraryBookModel =
    libraryBookStore.update(id, update)
}
