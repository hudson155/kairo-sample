package kairoSample.library.libraryBook

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import kairoSample.library.LibraryFeatureTest
import kairoSample.testing.ServerTest
import org.junit.jupiter.api.Test

class ListAllLibraryBooksTest : ServerTest by LibraryFeatureTest() {
  private val libraryBookService: LibraryBookService by lazy { koin.get() }

  // TODO: Replace this with real tests.

  @Test
  fun temp(): Unit =
    restTest {
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
