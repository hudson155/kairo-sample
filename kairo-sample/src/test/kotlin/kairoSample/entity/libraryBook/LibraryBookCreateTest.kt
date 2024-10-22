package kairoSample.entity.libraryBook

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kairo.restTesting.client
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryBookCreateTest : LibraryFeatureTest() {
  override suspend fun beforeEach() {
    setup("Create The Name of the Wind") {
      val creator = LibraryBookFixture.theNameOfTheWind.creator
      client.request(LibraryBookApi.Create(creator))
    }
  }

  @Test
  fun `duplicate email address`(): Unit = runTest {
    test("Create Arabian Nights with The Name of the Wind's ISBN") {
      val creator = LibraryBookFixture.arabianNights.creator.copy(
        isbn = LibraryBookFixture.theNameOfTheWind.creator.isbn,
      )
      shouldThrow<DuplicateLibraryBookIsbn> {
        client.request(LibraryBookApi.Create(creator))
      }
    }

    postcondition("Arabian Nights should not exist") {
      shouldThrow<LibraryBookNotFound> {
        client.request(LibraryBookApi.Get(LibraryBookFixture.arabianNights.rep.id))
      }
    }
  }

  @Test
  fun `happy path`(): Unit = runTest {
    val arabianNights = test("Create Arabian Nights") {
      val creator = LibraryBookFixture.arabianNights.creator
      return@test client.request(LibraryBookApi.Create(creator))
        .shouldBe(LibraryBookFixture.arabianNights.rep)
    }

    postcondition("Arabian Nights should exist") {
      client.request(LibraryBookApi.Get(LibraryBookFixture.arabianNights.rep.id))
        .shouldBe(arabianNights)
    }
  }
}
