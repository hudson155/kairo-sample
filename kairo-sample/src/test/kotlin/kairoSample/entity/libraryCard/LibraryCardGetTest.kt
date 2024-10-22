package kairoSample.entity.libraryCard

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kairo.restTesting.client
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryCardGetTest : LibraryFeatureTest() {
  override suspend fun beforeEach() {
    setup("Create Jeff 0") {
      val creator = LibraryCardFixture.jeff0.creator
      client.request(LibraryCardApi.Create(creator))
    }
  }

  @Test
  fun `does not exist`(): Unit = runTest {
    test("Jeff 1 should not exist") {
      shouldThrow<LibraryCardNotFound> {
        client.request(LibraryCardApi.Get(LibraryCardFixture.jeff1.rep.id))
      }
    }
  }

  @Test
  fun exists(): Unit = runTest {
    test("Jeff 0 should exist") {
      client.request(LibraryCardApi.Get(LibraryCardFixture.jeff0.rep.id))
        .shouldBe(LibraryCardFixture.jeff0.rep)
    }
  }
}
