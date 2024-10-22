package kairoSample.entity.libraryCard

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

internal class LibraryCardDeleteTest : LibraryFeatureTest() {
  override suspend fun beforeEach() {
    setup("Create Jeff 0") {
      val creator = LibraryCardFixture.jeff0.creator
      client.request(LibraryCardApi.Create(creator))
    }
  }

  @Test
  fun `does not exist`(): Unit = runTest {
    test("Delete Jeff 1") {
      shouldThrow<UnprocessableException> {
        client.request(LibraryCardApi.Delete(LibraryCardFixture.jeff1.rep.id))
      }.e.shouldBeInstanceOf<LibraryCardNotFound>()
    }
  }

  @Test
  fun `happy path`(): Unit = runTest {
    test("Delete Jeff 0") {
      client.request(LibraryCardApi.Delete(LibraryCardFixture.jeff0.rep.id))
        .shouldBe(LibraryCardFixture.jeff0.rep)
    }

    postcondition("Get Jeff 0") {
      shouldThrow<LibraryCardNotFound> {
        client.request(LibraryCardApi.Get(LibraryCardFixture.jeff0.rep.id))
      }
    }
  }
}
