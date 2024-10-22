package kairoSample.entity.libraryMember

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kairo.restTesting.client
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryMemberGetByEmailAddressTest : LibraryFeatureTest() {
  override suspend fun beforeEach() {
    setup("Create Jeff") {
      val creator = LibraryMemberFixture.jeff.creator
      client.request(LibraryMemberApi.Create(creator))
    }
  }

  @Test
  fun `does not exist`(): Unit = runTest {
    test("Noah should not exist") {
      shouldThrow<LibraryMemberNotFound> {
        client.request(LibraryMemberApi.GetByEmailAddress(LibraryMemberFixture.noah.rep.emailAddress))
      }
    }
  }

  @Test
  fun exists(): Unit = runTest {
    test("Jeff should exist") {
      client.request(LibraryMemberApi.GetByEmailAddress(LibraryMemberFixture.jeff.rep.emailAddress))
        .shouldBe(LibraryMemberFixture.jeff.rep)
    }
  }
}
