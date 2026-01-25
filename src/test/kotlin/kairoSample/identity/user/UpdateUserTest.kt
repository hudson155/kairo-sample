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
        userService.create(UserModel.Creator.jeffFixture())
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
      val jeff = setup {
        userService.create(UserModel.Creator.jeffFixture())
      }
      test {
        userService.update(jeff.id, UserModel.Update())
          .shouldBe(jeff)
      }
      postcondition {
        userService.get(jeff.id)
          .shouldBe(jeff)
      }
      postcondition {
        coVerify(exactly = 1) {
          stytchUsers.update(
            UserUpdateRequest(
              userId = jeff.id.value,
              name = UserName(
                firstName = jeff.firstName,
                lastName = jeff.lastName,
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
      val jeff = setup {
        userService.create(UserModel.Creator.jeffFixture())
      }
      test {
        userService.update(
          id = jeff.id,
          update = UserModel.Update(
            firstName = Required.Value("Noah"),
            lastName = Required.Value("Guld"),
          ),
        ).sanitized().shouldBe(
          UserModel.jeffFixture().copy(
            firstName = "Noah",
            lastName = "Guld",
          ),
        )
      }
      postcondition {
        coVerify(exactly = 1) {
          stytchUsers.update(
            UserUpdateRequest(
              userId = jeff.id.value,
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
