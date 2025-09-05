package kairoSample.library.libraryBook

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import kairo.testing.setup
import kairo.testing.test
import kairoSample.library.LibraryFeatureTest
import kairoSample.library.PerMethodDatabaseExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

// TODO: Enable & document testcontainers.reuse.enable=true and environment variables

@ExtendWith(PerMethodDatabaseExtension::class, LibraryFeatureTest::class)
internal class ListAllLibraryBooksTest {
  @Test
  fun `No library books exist`(libraryBookService: LibraryBookService): Unit =
    runTest {
      test { libraryBookService.listAll().shouldBeEmpty() }
    }

  @Test
  fun `2 library books exist`(libraryBookService: LibraryBookService): Unit =
    runTest {
      setup {
        libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
        libraryBookService.create(LibraryBookModel.Creator.theMeaningOfMarriage)
      }
      test {
        libraryBookService.listAll().map { it.sanitized() }.shouldContainExactlyInAnyOrder(
          LibraryBookModel.mereChristianity,
          LibraryBookModel.theMeaningOfMarriage,
        )
      }
    }
}
