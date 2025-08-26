package kairoSample.libraryBook

import kairo.id.IdGenerationStrategy
import kairo.id.IdGenerator
import org.koin.core.annotation.Single

@Single
class LibraryBookIdGenerator(
  strategy: IdGenerationStrategy,
) : IdGenerator<LibraryBookId>(strategy, prefix = "library_book") {
  override fun generate(value: String): LibraryBookId =
    LibraryBookId(value)
}
