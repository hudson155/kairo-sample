package kairoSample.entity.libraryCard

import io.kotest.matchers.shouldBe
import kairo.restTesting.client
import kairo.testing.postcondition
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryCardCreateTest : LibraryFeatureTest() {
  @Test
  fun `happy path`(): Unit = runTest {
    val jeff0 = test("Create Jeff 0") {
      val creator = LibraryCardFixture.jeff0.creator
      return@test client.request(LibraryCardApi.Create(creator))
        .shouldBe(LibraryCardFixture.jeff0.rep)
    }

    postcondition("Get Jeff 0") {
      client.request(LibraryCardApi.Get(LibraryCardFixture.jeff0.rep.id))
        .shouldBe(jeff0)
    }
  }
}
