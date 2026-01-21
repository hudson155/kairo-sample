package kairoSample.identity.user

import com.stytch.java.common.StytchResult
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kairo.exception.shouldThrow
import kairo.sql.PostgresExtension
import kairo.stytch.Stytch
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kairoSample.identity.IdentityFeatureTest
import kairoSample.identity.user.exception.UserNotFound
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import com.stytch.java.consumer.models.users.DeleteRequest as UserDeleteRequest

@ExtendWith(PostgresExtension::class, IdentityFeatureTest::class)
class DeleteUserTest {
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
        userService.create(UserModel.Creator.fixture())
      }
      test {
        shouldThrow(UserNotFound.unprocessable(UserId.zero)) {
          userService.delete(UserId.zero)
        }
      }
      postcondition {
        coVerify(exactly = 0) {
          stytchUsers.delete(any())
        }
      }
    }
  }

  @Test
  fun `Happy path`(
    stytch: Stytch,
    userService: UserService,
  ) {
    runTest {
      val stytchUsers = stytch.users
      setup {
        coEvery { stytchUsers.create(any()) } returns StytchResult.Success(mockk())
        coEvery { stytchUsers.delete(any()) } returns StytchResult.Success(mockk())
      }
      val user = setup {
        userService.create(UserModel.Creator.fixture())
      }
      test {
        userService.delete(user.id)
          .shouldBe(user)
      }
      postcondition {
        userService.get(user.id)
          .shouldBeNull()
      }
      postcondition {
        coVerify(exactly = 1) {
          stytchUsers.delete(
            UserDeleteRequest(
              userId = user.id.value,
            ),
          )
        }
      }
    }
  }
}
