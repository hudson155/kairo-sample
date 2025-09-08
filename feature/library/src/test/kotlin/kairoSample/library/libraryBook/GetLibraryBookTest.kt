package kairoSample.library.libraryBook

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kairo.sql.PostgresExtension
import kairo.testing.setup
import kairo.testing.test
import kairoSample.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PostgresExtension::class, LibraryFeatureTest::class)
internal class GetLibraryBookTest {
  @Test
  fun `Library book doesn't exist`(libraryBookService: LibraryBookService): Unit =
    runTest {
      setup {
        libraryBookService.create(LibraryBookModel.Creator.theMeaningOfMarriage)
      }
      test {
        libraryBookService.get(LibraryBookId.random())
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
