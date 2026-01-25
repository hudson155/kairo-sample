package kairoSample.identity.user

import com.stytch.java.common.StytchResult
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kairo.sql.PostgresExtension
import kairo.stytch.Stytch
import kairo.testing.setup
import kairo.testing.test
import kairoSample.identity.IdentityFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PostgresExtension::class, IdentityFeatureTest::class)
class GetUserTest {
  @Test
  fun `User doesn't exist`(
    stytch: Stytch,
    userService: UserService,
  ) {
    runTest {
      val stytchUsers = stytch.users
      setup {
        coEvery { stytchUsers.create(any()) } returns StytchResult.Success(mockk())
      }
      setup {
        userService.create(UserModel.Creator.jeffFixture())
      }
      test {
        userService.get(UserId.zero)
          .shouldBeNull()
      }
    }
  }

  @Test
  fun `User exists`(
    stytch: Stytch,
    userService: UserService,
  ) {
    runTest {
      val stytchUsers = stytch.users
      setup {
        coEvery { stytchUsers.create(any()) } returns StytchResult.Success(mockk())
      }
      val jeff = setup {
        userService.create(UserModel.Creator.jeffFixture())
      }
      test {
        userService.get(jeff.id)
          .shouldBe(jeff)
      }
    }
  }
}
