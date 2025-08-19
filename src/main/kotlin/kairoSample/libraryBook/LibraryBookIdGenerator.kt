package kairoSample.libraryBook

import kairo.id.Id
import kairo.id.IdGenerationStrategy
import kairo.id.IdGenerator
import org.koin.core.annotation.Single

@Single(createdAtStart = true)
class LibraryBookIdGenerator(
  strategy: IdGenerationStrategy,
) : IdGenerator<LibraryBookId>(strategy, prefix = "library_book") {
  override fun wrap(id: Id): LibraryBookId =
    LibraryBookId(id)
}
