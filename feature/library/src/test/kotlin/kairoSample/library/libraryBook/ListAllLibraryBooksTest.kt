package kairoSample.library.libraryBook

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import kairoSample.library.LibraryFeatureTest
import kairoSample.library.PerMethodDatabaseExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

// TODO: Document testcontainers.reuse.enable=true and environment variables

@ExtendWith(PerMethodDatabaseExtension::class, LibraryFeatureTest::class)
internal class ListAllLibraryBooksTest {
  // TODO: Replace this with real tests.

  @Test
  fun `none exist`(
    libraryBookService: LibraryBookService,
  ): Unit =
    runTest {
      libraryBookService.listAll()
        .shouldBeEmpty()
    }

  @Test
  fun `2 exist`(
    libraryBookService: LibraryBookService,
  ): Unit =
    runTest {
      libraryBookService.create(
        LibraryBookModel.Creator(
          title = "Mere Christianity",
          authors = listOf("C. S. Lewis"),
          isbn = "978-0060652920",
        ),
      )
      libraryBookService.create(
        LibraryBookModel.Creator(
          title = "The Meaning of Marriage: Facing the Complexities of Commitment with the Wisdom of God",
          authors = listOf("Timothy Keller", "Kathy Keller"),
          isbn = "978-1594631870",
        ),
      )
      libraryBookService.listAll()
        .shouldHaveSize(2)
    }
}
