package kairoSample.library.libraryBook

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import kairo.sql.PostgresExtension
import kairo.testing.setup
import kairo.testing.test
import kairoSample.library.LibraryFeatureTest
import kairoSample.libraryBook.LibraryBookModel
import kairoSample.libraryBook.LibraryBookService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PostgresExtension::class, LibraryFeatureTest::class)
internal class ListAllLibraryBooksTest {
  @Test
  fun `No library books exist`(libraryBookService: LibraryBookService): Unit =
    runTest {
      test {
        libraryBookService.listAll().toList()
          .shouldBeEmpty()
      }
    }

  @Test
  fun `2 library books exist`(libraryBookService: LibraryBookService): Unit =
    runTest {
      setup {
        libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
        libraryBookService.create(LibraryBookModel.Creator.theMeaningOfMarriage)
      }
      test {
        libraryBookService.listAll().map { it.sanitized() }.toList()
          .shouldContainExactlyInAnyOrder(
            LibraryBookModel.mereChristianity,
            LibraryBookModel.theMeaningOfMarriage,
          )
      }
    }
}
