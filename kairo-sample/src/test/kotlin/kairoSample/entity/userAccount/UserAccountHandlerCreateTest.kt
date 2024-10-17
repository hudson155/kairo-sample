package kairoSample.entity.userAccount

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kairo.restTesting.client
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class UserAccountHandlerCreateTest : UserAccountHandlerTest() {
  @Test
  fun `duplicate email address`(): Unit = runTest {
    setup("Create Jeff") {
      val creator = UserAccountFixture.jeff.creator
      client.request(UserAccountApi.Create(creator))
    }

    test("Create Noah with Jeff's email address") {
      val creator = UserAccountFixture.noah.creator.copy(
        emailAddress = UserAccountFixture.jeff.creator.emailAddress,
      )
      shouldThrow<DuplicateEmailAddress> {
        client.request(UserAccountApi.Create(creator))
      }
    }

    postcondition("Noah should not exist") {
      shouldThrow<UserAccountNotFound> {
        client.request(UserAccountApi.Get(UserAccountFixture.noah.rep.id))
      }
    }
  }

  @Test
  fun `happy path`(): Unit = runTest {
    test("Create Jeff") {
      val creator = UserAccountFixture.jeff.creator
      client.request(UserAccountApi.Create(creator))
        .shouldBe(UserAccountFixture.jeff.rep)
    }

    postcondition("Jeff should exist") {
      client.request(UserAccountApi.Get(UserAccountFixture.jeff.rep.id))
        .shouldBe(UserAccountFixture.jeff.rep)
    }
  }
}
