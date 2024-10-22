package kairoSample.entity.libraryMember

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kairo.restTesting.client
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryMemberCreateTest : LibraryFeatureTest() {
  override suspend fun beforeEach() {
    setup("Create Jeff") {
      val creator = LibraryMemberFixture.jeff.creator
      client.request(LibraryMemberApi.Create(creator))
    }
  }

  @Test
  fun `duplicate email address`(): Unit = runTest {
    test("Create Noah with Jeff's email address") {
      val creator = LibraryMemberFixture.noah.creator.copy(
        emailAddress = LibraryMemberFixture.jeff.creator.emailAddress,
      )
      shouldThrow<DuplicateLibraryMemberEmailAddress> {
        client.request(LibraryMemberApi.Create(creator))
      }
    }

    postcondition("Noah should not exist") {
      shouldThrow<LibraryMemberNotFound> {
        client.request(LibraryMemberApi.Get(LibraryMemberFixture.noah.rep.id))
      }
    }
  }

  @Test
  fun `happy path`(): Unit = runTest {
    val noah = test("Create Noah") {
      val creator = LibraryMemberFixture.noah.creator
      return@test client.request(LibraryMemberApi.Create(creator))
        .shouldBe(LibraryMemberFixture.noah.rep)
    }

    postcondition("Noah should exist") {
      client.request(LibraryMemberApi.Get(LibraryMemberFixture.noah.rep.id))
        .shouldBe(noah)
    }
  }
}
