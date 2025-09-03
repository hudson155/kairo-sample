package kairoSample.library.libraryBook

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kairo.coroutines.singleNullOrThrow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insert
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import org.koin.core.annotation.Single

private val logger: KLogger = KotlinLogging.logger {}

@Single
internal class LibraryBookStore(
  private val idGenerator: LibraryBookIdGenerator,
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

  suspend fun listAll(): List<LibraryBookModel> =
    suspendTransaction(db = database) {
      LibraryBookTable
        .selectAll()
        .map(LibraryBookModel::fromRow)
        .toList()
    }

  suspend fun create(creator: LibraryBookModel.Creator): LibraryBookModel {
    logger.info { "Creating library book (creator=$creator)." }
    return suspendTransaction(db = database) {
      val id = idGenerator.generate()
      LibraryBookTable.insert { statement ->
        statement[this.id] = id
        statement[this.title] = creator.title
        statement[this.authors] = creator.authors
        statement[this.isbn] = creator.isbn
      }
      checkNotNull(get(id))
    }
  }
}
