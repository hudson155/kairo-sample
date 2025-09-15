package kairoSample.library.libraryBook

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kairo.sql.postgres.uniqueViolation
import kairo.sql.postgres.withExceptionMappers
import kairoSample.library.libraryBook.exception.DuplicateLibraryBookIsbn
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.insertReturning
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.koin.core.annotation.Single

private val logger: KLogger = KotlinLogging.logger {}

@Single
internal class LibraryBookStore(
  private val database: Database,
) {
  fun get(id: LibraryBookId): LibraryBookModel? =
    transaction(db = database) {
      LibraryBookTable
        .selectAll()
        .where { LibraryBookTable.id eq id }
        .map(LibraryBookModel::fromRow)
        .singleNullOrThrow()
    }

  fun getByIsbn(isbn: String): LibraryBookModel? =
    transaction(db = database) {
      LibraryBookTable
        .selectAll()
        .where { LibraryBookTable.isbn eq isbn }
        .map(LibraryBookModel::fromRow)
        .singleNullOrThrow()
    }

  fun listAll(): List<LibraryBookModel> =
    transaction(db = database) {
      LibraryBookTable
        .selectAll()
        .map(LibraryBookModel::fromRow)
        .toList()
    }

  fun create(creator: LibraryBookModel.Creator): LibraryBookModel {
    logger.info { "Creating library book (creator=$creator)." }
    return withExceptionMappers(
      uniqueViolation("uq__library_book__isbn") { DuplicateLibraryBookIsbn(creator.isbn) },
    ) {
      transaction(db = database) {
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
}
