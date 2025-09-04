package kairoSample.library.libraryBook

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import kairoSample.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LibraryFeatureTest::class)
internal class ListAllLibraryBooksTest {
  // TODO: Replace this with real tests.

  @Test
  fun temp(
    libraryBookService: LibraryBookService,
  ): Unit =
    runTest {
      libraryBookService.listAll()
        .shouldBeEmpty()
      libraryBookService.create(
        LibraryBookModel.Creator(
          title = "Mere Christianity",
          authors = listOf("C. S. Lewis"),
          isbn = "978-0060652920",
        ),
      )
      libraryBookService.listAll()
        .shouldHaveSize(1)
    }
}
