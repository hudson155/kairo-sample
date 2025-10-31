package kairoSample.libraryBook

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kairo.coroutines.singleNullOrThrow
import kairo.optional.ifSpecified
import kairo.sql.postgres.uniqueViolation
import kairo.sql.postgres.withExceptionMappers
import kairoSample.libraryBook.exception.DuplicateLibraryBookIsbn
import kairoSample.libraryBook.exception.LibraryBookNotFound
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.deleteReturning
import org.jetbrains.exposed.v1.r2dbc.insertReturning
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.r2dbc.updateReturning
import org.koin.core.annotation.Single

private val logger: KLogger = KotlinLogging.logger {}

@Single
internal class LibraryBookStore(
  private val database: R2dbcDatabase,
) {
  suspend fun get(id: LibraryBookId): LibraryBookModel? =
    suspendTransaction(db = database) {
      LibraryBookTable
        .selectAll()
        .where { LibraryBookTable.id eq id }
        .map(LibraryBookModel::fromRow)
        .singleNullOrThrow()
    }

  suspend fun getByIsbn(isbn: String): LibraryBookModel? =
    suspendTransaction(db = database) {
      LibraryBookTable
        .selectAll()
        .where { LibraryBookTable.isbn eq isbn }
        .map(LibraryBookModel::fromRow)
        .singleNullOrThrow()
    }

  suspend fun listAll(): Flow<LibraryBookModel> =
    suspendTransaction(db = database) {
      LibraryBookTable
        .selectAll()
        .map(LibraryBookModel::fromRow)
    }

  suspend fun create(creator: LibraryBookModel.Creator): LibraryBookModel {
    logger.info { "Creating library book (creator=$creator)." }
    return withExceptionMappers(
      uniqueViolation("uq__library_book__isbn") { DuplicateLibraryBookIsbn(creator.isbn) },
    ) {
      suspendTransaction(db = database) {
        LibraryBookTable
          .insertReturning { statement ->
            statement[this.id] = LibraryBookId.random()
            statement[this.title] = creator.title
            statement[this.authors] = creator.authors
            statement[this.isbn] = creator.isbn
          }
          .map(LibraryBookModel::fromRow)
          .single()
      }
    }
  }

  suspend fun update(id: LibraryBookId, update: LibraryBookModel.Update): LibraryBookModel {
    logger.info { "Updating library book (id=$id)." }
    if (!update.hasUpdates()) return get(id) ?: throw LibraryBookNotFound.unprocessable(id)
    return suspendTransaction(db = database) {
      LibraryBookTable
        .updateReturning(where = { LibraryBookTable.id eq id }) { statement ->
          update.title.ifSpecified { title -> statement[this.title] = title }
          update.authors.ifSpecified { authors -> statement[this.authors] = authors }
          update.isbn.ifSpecified { isbn -> statement[this.isbn] = isbn }
        }
        .map(LibraryBookModel::fromRow)
        .singleNullOrThrow()
        ?: throw LibraryBookNotFound.unprocessable(id)
    }
  }

  suspend fun delete(id: LibraryBookId): LibraryBookModel {
    logger.info { "Deleting library book (id=$id)." }
    return suspendTransaction(db = database) {
      LibraryBookTable
        .deleteReturning(where = { LibraryBookTable.id eq id })
        .map(LibraryBookModel::fromRow)
        .singleNullOrThrow()
        ?: throw LibraryBookNotFound.unprocessable(id)
    }
  }
}
