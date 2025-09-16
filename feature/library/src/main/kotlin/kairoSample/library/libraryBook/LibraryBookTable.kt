package kairoSample.library.libraryBook

import kotlin.time.Instant
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp

internal object LibraryBookTable : Table("library.library_book") {
  val id: Column<LibraryBookId> =
    text("id")
      .transform(::LibraryBookId, LibraryBookId::value)

  override val primaryKey: PrimaryKey = PrimaryKey(id)

  val createdAt: Column<Instant> =
    timestamp("created_at")
      .defaultExpression(CurrentTimestamp)

  val version: Column<Long> =
    long("version")
      .default(0)

  val updatedAt: Column<Instant> =
    timestamp("updated_at")
      .defaultExpression(CurrentTimestamp)

  val title: Column<String?> =
    text("title").nullable()

  val authors: Column<List<String>> =
    array<String>("authors")

  val isbn: Column<String> =
    text("isbn")
      .uniqueIndex("uq__library_book__isbn")
}

internal fun LibraryBookModel.Companion.fromRow(row: ResultRow): LibraryBookModel =
  LibraryBookModel(
    id = row[LibraryBookTable.id],
    createdAt = row[LibraryBookTable.createdAt],
    title = row[LibraryBookTable.title],
    authors = row[LibraryBookTable.authors],
    isbn = row[LibraryBookTable.isbn],
  )
