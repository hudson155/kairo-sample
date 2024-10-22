package kairoSample.entity.libraryBook

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kairo.restTesting.client
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryBookGetTest : LibraryFeatureTest() {
  override suspend fun beforeEach() {
    setup("Create The Name of the Wind") {
      val creator = LibraryBookFixture.theNameOfTheWind.creator
      client.request(LibraryBookApi.Create(creator))
    }
  }

  @Test
  fun `does not exist`(): Unit = runTest {
    test("Arabian Nights should not exist") {
      shouldThrow<LibraryBookNotFound> {
        client.request(LibraryBookApi.Get(LibraryBookFixture.arabianNights.rep.id))
      }
    }
  }

  @Test
  fun exists(): Unit = runTest {
    test("The Name of the Wind should exist") {
      client.request(LibraryBookApi.Get(LibraryBookFixture.theNameOfTheWind.rep.id))
        .shouldBe(LibraryBookFixture.theNameOfTheWind.rep)
    }
  }
}
