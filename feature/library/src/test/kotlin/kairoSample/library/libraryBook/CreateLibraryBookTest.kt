package kairoSample.library.libraryBook

import io.kotest.matchers.shouldBe
import kairo.exception.shouldThrow
import kairo.sql.PostgresExtension
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kairoSample.library.LibraryFeatureTest
import kairoSample.library.libraryBook.exception.DuplicateLibraryBookIsbn
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PostgresExtension::class, LibraryFeatureTest::class)
internal class CreateLibraryBookTest {
  @Test
  fun `Happy path`(libraryBookService: LibraryBookService): Unit =
    runTest {
      val mereChristianity = test {
        val created = libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
        created.sanitized().shouldBe(LibraryBookModel.mereChristianity)
        return@test created
      }
      postcondition {
        libraryBookService.get(mereChristianity.id)
          .shouldBe(mereChristianity)
      }
    }

  @Test
  fun `Duplicate ISBN`(libraryBookService: LibraryBookService): Unit =
    runTest {
      setup {
        libraryBookService.create(
          LibraryBookModel.Creator.theMeaningOfMarriage.copy(
            isbn = LibraryBookModel.mereChristianity.isbn,
          ),
        )
      }
      test {
        shouldThrow(DuplicateLibraryBookIsbn(LibraryBookModel.Creator.mereChristianity.isbn)) {
          libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
        }
      }
    }
}
