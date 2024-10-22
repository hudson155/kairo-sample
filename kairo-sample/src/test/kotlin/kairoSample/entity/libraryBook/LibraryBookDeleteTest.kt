package kairoSample.entity.libraryBook

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kairo.exception.UnprocessableException
import kairo.restTesting.client
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryBookDeleteTest : LibraryFeatureTest() {
  override suspend fun beforeEach() {
    setup("Create The Name of the Wind") {
      val creator = LibraryBookFixture.theNameOfTheWind.creator
      client.request(LibraryBookApi.Create(creator))
    }
  }

  @Test
  fun `does not exist`(): Unit = runTest {
    test("Delete Arabian Nights") {
      shouldThrow<UnprocessableException> {
        client.request(LibraryBookApi.Delete(LibraryBookFixture.arabianNights.rep.id))
      }.e.shouldBeInstanceOf<LibraryBookNotFound>()
    }
  }

  @Test
  fun `happy path`(): Unit = runTest {
    test("Delete The Name of the Wind") {
      client.request(LibraryBookApi.Delete(LibraryBookFixture.theNameOfTheWind.rep.id))
        .shouldBe(LibraryBookFixture.theNameOfTheWind.rep)
    }

    postcondition("Get The Name of the Wind") {
      shouldThrow<LibraryBookNotFound> {
        client.request(LibraryBookApi.Get(LibraryBookFixture.theNameOfTheWind.rep.id))
      }
    }
  }
}
