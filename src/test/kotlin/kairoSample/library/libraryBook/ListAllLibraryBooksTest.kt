package kairoSample.library.libraryBook

import io.kotest.matchers.collections.shouldHaveSize
import kairoSample.library.LibraryFeatureTest
import kairoSample.testing.ServerTest
import org.junit.jupiter.api.Test

class ListAllLibraryBooksTest : ServerTest by LibraryFeatureTest() {
  // TODO: Replace this with real tests.
  @Test
  fun temp(): Unit =
    restTest {
      koin.get<LibraryBookService>().listAll()
        .shouldHaveSize(2)
    }
}
