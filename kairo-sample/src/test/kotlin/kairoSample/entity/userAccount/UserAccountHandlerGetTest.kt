package kairoSample.entity.userAccount

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kairo.restTesting.client
import kairo.testing.setup
import kairo.testing.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class UserAccountHandlerGetTest : UserAccountHandlerTest() {
  @Test
  fun `does not exist`(): Unit = runTest {
    setup("Create Jeff") {
      val creator = UserAccountFixture.jeff.creator
      client.request(UserAccountApi.Create(creator))
    }

    test("Noah should not exist") {
      shouldThrow<UserAccountNotFound> {
        client.request(UserAccountApi.Get(UserAccountFixture.noah.rep.id))
      }
    }
  }

  @Test
  fun exists(): Unit = runTest {
    setup("Create Jeff") {
      val creator = UserAccountFixture.jeff.creator
      client.request(UserAccountApi.Create(creator))
    }

    test("Jeff should exist") {
      client.request(UserAccountApi.Get(UserAccountFixture.jeff.rep.id))
        .shouldBe(UserAccountFixture.jeff.rep)
    }
  }
}
