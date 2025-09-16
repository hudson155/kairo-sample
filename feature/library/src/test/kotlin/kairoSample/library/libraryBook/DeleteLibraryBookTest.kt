package kairoSample.library.libraryBook

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kairo.exception.shouldThrow
import kairo.sql.PostgresExtension
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kairoSample.library.LibraryFeatureTest
import kairoSample.library.libraryBook.exception.LibraryBookNotFound
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PostgresExtension::class, LibraryFeatureTest::class)
internal class DeleteLibraryBookTest {
  @Test
  fun `Library book doesn't exist`(libraryBookService: LibraryBookService): Unit =
    runTest {
      setup {
        libraryBookService.create(LibraryBookModel.Creator.theMeaningOfMarriage)
      }
      test {
        shouldThrow(LibraryBookNotFound.unprocessable(LibraryBookId.zero)) {
          libraryBookService.delete(LibraryBookId.zero)
        }
      }
    }

  @Test
  fun `Happy path`(libraryBookService: LibraryBookService): Unit =
    runTest {
      val mereChristianity = setup {
        libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
      }
      test {
        libraryBookService.delete(mereChristianity.id)
          .shouldBe(mereChristianity)
      }
      postcondition {
        libraryBookService.get(mereChristianity.id)
          .shouldBeNull()
      }
    }
}
