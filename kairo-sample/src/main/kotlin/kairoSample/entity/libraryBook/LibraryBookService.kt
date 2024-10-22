package kairoSample.entity.libraryBook

import com.google.inject.Inject
import kairo.id.KairoId
import kairo.updater.Updater

internal class LibraryBookService @Inject constructor(
  private val libraryBookStore: LibraryBookStore,
) {
  suspend fun get(id: KairoId): LibraryBookModel? =
    libraryBookStore.get(id)

  suspend fun getByIsbn(isbn: String): LibraryBookModel? =
    libraryBookStore.getByIsbn(isbn)

  suspend fun listAll(): List<LibraryBookModel> =
    libraryBookStore.listAll()

  suspend fun searchByText(title: String?, author: String?): List<LibraryBookModel> =
    libraryBookStore.searchByText(title = title, author = author)

  suspend fun create(creator: LibraryBookModel.Creator): LibraryBookModel =
    libraryBookStore.create(creator)

  suspend fun update(id: KairoId, updater: Updater<LibraryBookModel.Update>): LibraryBookModel =
    libraryBookStore.update(id, updater)

  suspend fun delete(id: KairoId): LibraryBookModel =
    libraryBookStore.delete(id)
}
