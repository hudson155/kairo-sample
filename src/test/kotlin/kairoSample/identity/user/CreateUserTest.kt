package kairoSample.identity.user

import com.stytch.java.common.StytchResult
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
import kairoSample.identity.user.exception.EmailAddressAlreadyInUse
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import com.stytch.java.consumer.models.users.CreateRequest as UserCreateRequest
import com.stytch.java.consumer.models.users.Name as UserName

@ExtendWith(PostgresExtension::class, IdentityFeatureTest::class)
class CreateUserTest {
  @Test
  fun `Happy path`(
    stytch: Stytch,
    userService: UserService,
  ) {
    runTest {
      val stytchUsers = stytch.users
      setup {
        coEvery { stytchUsers.create(any()) } returns StytchResult.Success(mockk())
      }
      val jeff = test {
        val jeff = userService.create(UserModel.Creator.jeffFixture())
        jeff.sanitized().shouldBe(UserModel.jeffFixture())
        return@test jeff
      }
      postcondition {
        userService.get(jeff.id)
          .shouldBe(jeff)
      }
      postcondition {
        coVerify(exactly = 1) {
          stytchUsers.create(
            UserCreateRequest(
              email = jeff.emailAddress,
              name = UserName(
                firstName = jeff.firstName,
                lastName = jeff.lastName,
              ),
              externalId = jeff.id.value,
            ),
          )
        }
      }
    }
  }

  @Test
  fun `Email address already in use`(
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
        shouldThrow(EmailAddressAlreadyInUse(jeff.emailAddress)) {
          userService.create(UserModel.Creator.jeffFixture())
        }
      }
    }
  }
}
