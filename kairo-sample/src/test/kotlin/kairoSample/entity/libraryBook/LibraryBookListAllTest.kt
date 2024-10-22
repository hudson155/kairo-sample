package kairoSample.entity.libraryBook

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import kairo.restTesting.client
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryBookListAllTest : LibraryFeatureTest() {
  @Test
  fun empty(): Unit = runTest {
    test("List all") {
      client.request(LibraryBookApi.ListAll)
        .shouldBeEmpty()
    }
  }

  @Test
  fun `non-empty`(): Unit = runTest {
    setup("Create The Name of the Wind") {
      val creator = LibraryBookFixture.theNameOfTheWind.creator
      client.request(LibraryBookApi.Create(creator))
    }

    setup("Create Arabian Nights") {
      val creator = LibraryBookFixture.arabianNights.creator
      client.request(LibraryBookApi.Create(creator))
    }

    test("List all") {
      client.request(LibraryBookApi.ListAll)
        .shouldContainExactlyInAnyOrder(
          LibraryBookFixture.theNameOfTheWind.rep,
          LibraryBookFixture.arabianNights.rep,
        )
    }
  }
}
