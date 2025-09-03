package kairoSample.library.libraryBook

import kairo.id.IdGenerationStrategy
import kairo.id.IdGenerator
import org.koin.core.annotation.Single

@Single
internal class LibraryBookIdGenerator(
  strategy: IdGenerationStrategy,
) : IdGenerator<LibraryBookId>(strategy, prefix = "library_book") {
  override fun generate(value: String): LibraryBookId =
    LibraryBookId(value)
}
