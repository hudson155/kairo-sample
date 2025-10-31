package kairoSample.libraryBook

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
internal class LibraryBookService(
  private val libraryBookStore: LibraryBookStore,
) {
  suspend fun get(id: LibraryBookId): LibraryBookModel? =
    libraryBookStore.get(id)

  suspend fun getByIsbn(isbn: String): LibraryBookModel? =
    libraryBookStore.getByIsbn(isbn)

  suspend fun listAll(): Flow<LibraryBookModel> =
    libraryBookStore.listAll()

  suspend fun create(creator: LibraryBookModel.Creator): LibraryBookModel =
    libraryBookStore.create(creator)

  suspend fun update(id: LibraryBookId, update: LibraryBookModel.Update): LibraryBookModel =
    libraryBookStore.update(id, update)

  suspend fun delete(id: LibraryBookId): LibraryBookModel =
    libraryBookStore.delete(id)
}
