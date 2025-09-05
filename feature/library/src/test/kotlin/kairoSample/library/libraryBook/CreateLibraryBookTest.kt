package kairoSample.library.libraryBook

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.shouldBe
import kairo.testing.postcondition
import kairo.testing.test
import kairoSample.library.LibraryFeatureTest
import kairoSample.library.PerMethodDatabaseExtension
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PerMethodDatabaseExtension::class, LibraryFeatureTest::class)
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
        libraryBookService.get(mereChristianity.id)?.sanitized()
          .shouldBe(LibraryBookModel.mereChristianity)
      }
    }

  @Test
  fun `Duplicate ISBN`(libraryBookService: LibraryBookService): Unit =
    runBlocking {
      libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
      shouldThrowAny { // TODO: This exception should be mapped.
        libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
      }
    }
}
