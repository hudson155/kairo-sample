package kairoSample.entity.libraryBook

import com.google.inject.Inject
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kairo.exception.unprocessable
import kairo.id.KairoId
import kairo.sql.store.isUniqueViolation
import kairo.updater.Updater
import kairoSample.common.KairoSampleSqlStore
import org.jdbi.v3.core.kotlin.bindKotlin
import org.postgresql.util.ServerErrorMessage

private val logger: KLogger = KotlinLogging.logger {}

internal class LibraryBookStore @Inject constructor() : KairoSampleSqlStore<LibraryBookModel>(
  tableName = "library.library_book",
) {
  suspend fun getByIsbn(isbn: String): LibraryBookModel? =
    sql { handle ->
      val query = handle.createQuery(rs("store/libraryBook/getByIsbn.sql"))
      query.bind("isbn", isbn)
      return@sql query.mapToType().singleNullOrThrow()
    }

  suspend fun listAll(): List<LibraryBookModel> =
    sql { handle ->
      val query = handle.createQuery(rs("store/libraryBook/listAll.sql"))
      return@sql query.mapToType().toList()
    }

  suspend fun searchByText(title: String?, author: String?): List<LibraryBookModel> =
    sql { handle ->
      val query = handle.createQuery(rs("store/libraryBook/searchByText.sql"))
      query.bind("title", title)
      query.bind("author", author)
      return@sql query.mapToType().toList()
    }

  suspend fun create(creator: LibraryBookModel.Creator): LibraryBookModel =
    sql { handle ->
      logger.info { "Creating library book: $creator." }
      val query = handle.createQuery(rs("store/libraryBook/create.sql"))
      query.bindKotlin(creator)
      return@sql query.mapToType().single()
    }

  suspend fun update(
    id: KairoId,
    updater: Updater<LibraryBookModel.Update>,
  ): LibraryBookModel =
    sql { handle ->
      val libraryBook = get(id, forUpdate = true) ?: throw unprocessable(LibraryBookNotFound(id))
      val update = updater.update(LibraryBookModel.Update(libraryBook))
      logger.info { "Updating library book: $update." }
      val query = handle.createQuery(rs("store/libraryBook/update.sql"))
      query.bind("id", id)
      query.bindKotlin(update)
      return@sql query.mapToType().single()
    }

  suspend fun delete(id: KairoId): LibraryBookModel =
    sql { handle ->
      logger.info { "Deleting library book: $id." }
      val query = handle.createQuery(rs("store/libraryBook/delete.sql"))
      query.bind("id", id)
      return@sql query.mapToType().singleNullOrThrow() ?: throw unprocessable(LibraryBookNotFound(id))
    }

  override fun ServerErrorMessage.onError() {
    when {
      isUniqueViolation("uq__library_book__isbn") -> throw DuplicateLibraryBookIsbn()
    }
  }
}
