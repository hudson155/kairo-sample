package kairoSample.entity.libraryBook

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import kairo.restTesting.client
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryBookSearchByTextTest : LibraryFeatureTest() {
  override suspend fun beforeEach() {
    setup("Create The Name of the Wind") {
      val creator = LibraryBookFixture.theNameOfTheWind.creator
      client.request(LibraryBookApi.Create(creator))
    }

    setup("Create Arabian Nights") {
      val creator = LibraryBookFixture.arabianNights.creator
      client.request(LibraryBookApi.Create(creator))
    }
  }

  @Test
  fun empty(): Unit = runTest {
    test("Search by text") {
      client.request(
        LibraryBookApi.SearchByText(
          title = "ame",
          author = "foo",
        ),
      ).shouldBeEmpty()
    }
  }

  @Test
  fun `one result by title`(): Unit = runTest {
    test("Search by text") {
      client.request(
        LibraryBookApi.SearchByText(
          title = "ARA",
          author = null,
        ),
      ).shouldContainExactlyInAnyOrder(
        LibraryBookFixture.arabianNights.rep,
      )
    }
  }

  @Test
  fun `one result by author`(): Unit = runTest {
    test("Search by text") {
      client.request(
        LibraryBookApi.SearchByText(
          title = null,
          author = "A",
        ),
      ).shouldContainExactlyInAnyOrder(
        LibraryBookFixture.theNameOfTheWind.rep,
      )
    }
  }

  @Test
  fun `one result by title and author`(): Unit = runTest {
    test("Search by text") {
      client.request(
        LibraryBookApi.SearchByText(
          title = "A",
          author = "A",
        ),
      ).shouldContainExactlyInAnyOrder(
        LibraryBookFixture.theNameOfTheWind.rep,
      )
    }
  }

  @Test
  fun `multiple results by title`(): Unit = runTest {
    test("Search by text") {
      client.request(
        LibraryBookApi.SearchByText(
          title = "A",
          author = null,
        ),
      ).shouldContainExactlyInAnyOrder(
        LibraryBookFixture.theNameOfTheWind.rep,
        LibraryBookFixture.arabianNights.rep,
      )
    }
  }
}
