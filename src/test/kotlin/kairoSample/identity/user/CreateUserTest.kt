package kairoSample.identity.user

import com.stytch.java.common.StytchResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kairo.sql.PostgresExtension
import kairo.stytch.Stytch
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kairoSample.identity.IdentityFeatureTest
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
      val user = test {
        val user = userService.create(UserModel.Creator.fixture())
        user.sanitized().shouldBe(UserModel.fixture())
        return@test user
      }
      postcondition {
        userService.get(user.id)
          .shouldBe(user)
      }
      postcondition {
        coVerify(exactly = 1) {
          stytchUsers.create(
            UserCreateRequest(
              email = user.emailAddress,
              name = UserName(
                firstName = user.firstName,
                lastName = user.lastName,
              ),
              externalId = user.id.value,
            ),
          )
        }
      }
    }
  }
}
