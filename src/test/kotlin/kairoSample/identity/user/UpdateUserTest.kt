package kairoSample.identity.user

import com.stytch.java.common.StytchResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kairo.exception.shouldThrow
import kairo.optional.Required
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
import com.stytch.java.consumer.models.users.Name as UserName
import com.stytch.java.consumer.models.users.UpdateRequest as UserUpdateRequest

@ExtendWith(PostgresExtension::class, IdentityFeatureTest::class)
class UpdateUserTest {
  @Test
  fun `User doesn't exist`(
    stytch: Stytch,
    userService: UserService,
  ): Unit =
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
          userService.update(UserId.zero, UserModel.Update())
        }
      }
      postcondition {
        coVerify(exactly = 0) { stytchUsers.update(any()) }
      }
    }

  @Test
  fun `No properties updated`(
    stytch: Stytch,
    userService: UserService,
  ): Unit =
    runTest {
      val stytchUsers = stytch.users
      setup {
        coEvery { stytchUsers.create(any()) } returns StytchResult.Success(mockk())
        coEvery { stytchUsers.update(any()) } returns StytchResult.Success(mockk())
      }
      val user = setup {
        userService.create(UserModel.Creator.fixture())
      }
      test {
        userService.update(user.id, UserModel.Update())
          .shouldBe(user)
      }
      postcondition {
        userService.get(user.id)
          .shouldBe(user)
      }
      postcondition {
        coVerify(exactly = 1) {
          stytchUsers.update(
            UserUpdateRequest(
              userId = user.id.value,
              name = UserName(
                firstName = user.firstName,
                lastName = user.lastName,
              ),
            ),
          )
        }
      }
    }

  @Test
  fun `All properties updated`(
    stytch: Stytch,
    userService: UserService,
  ): Unit =
    runTest {
      val stytchUsers = stytch.users
      setup {
        coEvery { stytchUsers.create(any()) } returns StytchResult.Success(mockk())
        coEvery { stytchUsers.update(any()) } returns StytchResult.Success(mockk())
      }
      val user = setup {
        userService.create(UserModel.Creator.fixture())
      }
      test {
        userService.update(
          id = user.id,
          update = UserModel.Update(
            firstName = Required.Value("Noah"),
            lastName = Required.Value("Guld"),
          ),
        ).sanitized().shouldBe(
          UserModel.fixture().copy(
            firstName = "Noah",
            lastName = "Guld",
          ),
        )
      }
      postcondition {
        coVerify(exactly = 1) {
          stytchUsers.update(
            UserUpdateRequest(
              userId = user.id.value,
              name = UserName(
                firstName = "Noah",
                lastName = "Guld",
              ),
            ),
          )
        }
      }
    }
}
