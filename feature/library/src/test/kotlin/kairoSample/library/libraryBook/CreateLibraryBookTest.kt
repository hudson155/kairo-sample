package kairoSample.library.libraryBook

import io.kotest.matchers.shouldBe
import kairo.testing.postcondition
import kairo.testing.test
import kairoSample.library.LibraryFeatureTest
import kairoSample.library.PerMethodDatabaseExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PerMethodDatabaseExtension::class, LibraryFeatureTest::class)
internal class CreateLibraryBookTest {
  @Test
  fun `happy path`(libraryBookService: LibraryBookService): Unit =
    runTest {
      val mereChristianity = test {
        libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
          .also { it.sanitized().shouldBe(LibraryBookModel.mereChristianity) }
      }
      postcondition {
        libraryBookService.get(mereChristianity.id)?.sanitized()
          .shouldBe(LibraryBookModel.mereChristianity)
      }
    }
}
