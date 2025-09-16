package kairoSample.library.libraryBook

import io.kotest.matchers.shouldBe
import kairo.exception.shouldThrow
import kairo.optional.Optional
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
internal class UpdateLibraryBookTest {
  @Test
  fun `Library book doesn't exist`(libraryBookService: LibraryBookService): Unit =
    runTest {
      setup {
        libraryBookService.create(LibraryBookModel.Creator.theMeaningOfMarriage)
      }
      test {
        shouldThrow(LibraryBookNotFound.unprocessable(LibraryBookId.zero)) {
          libraryBookService.update(LibraryBookId.zero, LibraryBookModel.Update())
        }
      }
    }

  @Test
  fun `No properties updated`(libraryBookService: LibraryBookService): Unit =
    runTest {
      val mereChristianity = setup {
        libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
      }
      test {
        libraryBookService.update(mereChristianity.id, LibraryBookModel.Update())
          .shouldBe(mereChristianity)
      }
      postcondition {
        libraryBookService.get(mereChristianity.id)
          .shouldBe(mereChristianity)
      }
    }

  @Test
  fun `All properties updated`(libraryBookService: LibraryBookService): Unit =
    runTest {
      val mereChristianity = setup {
        libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
      }
      test {
        libraryBookService.update(
          id = mereChristianity.id,
          update = LibraryBookModel.Update(
            title = Optional.fromNullable(LibraryBookModel.theMeaningOfMarriage.title),
            authors = LibraryBookModel.theMeaningOfMarriage.authors,
            isbn = LibraryBookModel.theMeaningOfMarriage.isbn,
          ),
        ).sanitized().shouldBe(LibraryBookModel.theMeaningOfMarriage)
      }
    }

  @Test
  fun `Title to null`(libraryBookService: LibraryBookService): Unit =
    runTest {
      val mereChristianity = setup {
        libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
      }
      test {
        libraryBookService.update(
          id = mereChristianity.id,
          update = LibraryBookModel.Update().copy(title = Optional.Null),
        ).shouldBe(mereChristianity.copy(title = null))
      }
    }

  @Test
  fun `Authors to empty`(libraryBookService: LibraryBookService): Unit =
    runTest {
      val mereChristianity = setup {
        libraryBookService.create(LibraryBookModel.Creator.mereChristianity)
      }
      test {
        libraryBookService.update(
          id = mereChristianity.id,
          update = LibraryBookModel.Update().copy(authors = emptyList()),
        ).shouldBe(mereChristianity.copy(authors = emptyList()))
      }
    }
}
