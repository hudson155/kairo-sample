package kairoSample.entity.libraryBook

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.util.Optional
import kairo.exception.UnprocessableException
import kairo.restTesting.client
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryBookUpdateTest : LibraryFeatureTest() {
  private val emptyUpdate: LibraryBookRep.Update =
    LibraryBookRep.Update()

  private val completeUpdate: LibraryBookRep.Update =
    LibraryBookRep.Update(
      title = "New title",
      author = Optional.of("New author"),
    )

  override suspend fun beforeEach() {
    setup("Create The Name of the Wind") {
      val creator = LibraryBookFixture.theNameOfTheWind.creator
      client.request(LibraryBookApi.Create(creator))
    }
  }

  @Test
  fun `does not exist`(): Unit = runTest {
    test("Update Arabian Nights") {
      shouldThrow<UnprocessableException> {
        client.request(LibraryBookApi.Update(LibraryBookFixture.arabianNights.rep.id, completeUpdate))
      }.e.shouldBeInstanceOf<LibraryBookNotFound>()
    }

    postcondition("Get Arabian Nights") {
      shouldThrow<LibraryBookNotFound> {
        client.request(LibraryBookApi.Get(LibraryBookFixture.arabianNights.rep.id))
      }
    }
  }

  @Test
  fun `happy path (empty)`(): Unit = runTest {
    val theNameOfTheWind = LibraryBookFixture.theNameOfTheWind.rep

    test("Update The Name of the Wind") {
      client.request(LibraryBookApi.Update(LibraryBookFixture.theNameOfTheWind.rep.id, emptyUpdate))
        .shouldBe(theNameOfTheWind)
    }
  }

  @Test
  fun `happy path (complete)`(): Unit = runTest {
    val theNameOfTheWind = LibraryBookFixture.theNameOfTheWind.rep.copy(
      title = "New title",
      author = "New author",
    )

    test("Update The Name of the Wind") {
      client.request(LibraryBookApi.Update(LibraryBookFixture.theNameOfTheWind.rep.id, completeUpdate))
        .shouldBe(theNameOfTheWind)
    }

    postcondition("Get The Name of the Wind") {
      client.request(LibraryBookApi.Get(LibraryBookFixture.theNameOfTheWind.rep.id))
        .shouldBe(theNameOfTheWind)
    }
  }
}
