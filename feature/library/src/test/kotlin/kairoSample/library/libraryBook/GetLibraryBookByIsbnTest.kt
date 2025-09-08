package kairoSample.library.libraryBook

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kairo.testing.setup
import kairo.testing.test
import kairoSample.library.LibraryFeatureTest
import kairoSample.library.PerMethodDatabaseExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PerMethodDatabaseExtension::class, LibraryFeatureTest::class)
internal class GetLibraryBookByIsbnTest {
  @Test
  fun `Library book doesn't exist`(libraryBookService: LibraryBookService): Unit =
    runTest {
      setup {
        libraryBookService.create(LibraryBookModel.Creator.theMeaningOfMarriage)
      }
      test {
        libraryBookService.getByIsbn(LibraryBookModel.mereChristianity.isbn)
          .shouldBeNull()
      }
    }

  @Test
  fun `Library book exists`(libraryBookService: LibraryBookService): Unit =
    runTest {
      val mereChristianity = setup {
        libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
      }
      test {
        libraryBookService.get(mereChristianity.id)?.sanitized()
          .shouldBe(LibraryBookModel.mereChristianity)
      }
    }
}
