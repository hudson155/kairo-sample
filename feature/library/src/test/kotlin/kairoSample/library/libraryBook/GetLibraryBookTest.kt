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
internal class GetLibraryBookTest {
  @Test
  fun `library book doesn't exist`(libraryBookService: LibraryBookService): Unit =
    runTest {
      setup { libraryBookService.create(LibraryBookModel.Creator.mereChristianity) }
      test { libraryBookService.get(LibraryBookId.random()).shouldBeNull() }
    }

  @Test
  fun `library book exists`(libraryBookService: LibraryBookService): Unit =
    runTest {
      val mereChristianity = setup { libraryBookService.create(LibraryBookModel.Creator.mereChristianity) }
      test {
        libraryBookService.get(mereChristianity.id)
          .also { it?.sanitized().shouldBe(LibraryBookModel.mereChristianity) }
      }
    }
}
