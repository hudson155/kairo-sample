package kairoSample.libraryBook

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.time.Clock
import org.koin.core.annotation.Single

private val logger: KLogger = KotlinLogging.logger {}

// TODO: Use SQL.
@Single(createdAtStart = true)
class LibraryBookStore(
  private val idGenerator: LibraryBookIdGenerator,
) {
  // This is not thread-safe.
  private val store: MutableMap<LibraryBookId, LibraryBookModel> =
    listOf(
      LibraryBookModel(
        id = idGenerator.generate(),
        createdAt = Clock.System.now(),
        title = "Mere Christianity",
        authors = listOf("C. S. Lewis"),
        isbn = "978-0060652920",
      ),
      LibraryBookModel(
        id = idGenerator.generate(),
        createdAt = Clock.System.now(),
        title = "The Meaning of Marriage: Facing the Complexities of Commitment with the Wisdom of God",
        authors = listOf("Timothy Keller", "Kathy Keller"),
        isbn = "978-1594631870",
      ),
    ).associateBy { it.id }.toMutableMap()

  fun get(id: LibraryBookId): LibraryBookModel? =
    store[id]

  fun listAll(): List<LibraryBookModel> =
    store.values.toList()

  fun create(creator: LibraryBookModel.Creator): LibraryBookModel {
    logger.info { "Creating library book (creator=$creator)." }
    val id = idGenerator.generate()
    val libraryBook = LibraryBookModel(
      id = id,
      createdAt = Clock.System.now(),
      title = creator.title,
      authors = creator.authors,
      isbn = creator.isbn,
    )
    store[id] = libraryBook
    return libraryBook
  }
}
