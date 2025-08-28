package kairoSample.library.libraryBook

import io.kotest.matchers.collections.shouldBeEmpty
import kairoSample.library.LibraryFeatureTest
import kairoSample.testing.ServerTest
import org.junit.jupiter.api.Test

class ListAllLibraryBooksTest : ServerTest by LibraryFeatureTest() {
  @Test
  fun temp(): Unit =
    restTest {
      koin.get<LibraryBookService>().listAll()
        .shouldBeEmpty()
    }
}
