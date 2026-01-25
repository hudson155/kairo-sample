package kairoSample.identity.user

import com.stytch.java.common.StytchResult
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
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
class ListAllUsersTest {
  @Test
  fun `No users exist`(
    userService: UserService,
  ) {
    runTest {
      test {
        userService.listAll()
          .shouldBeEmpty()
      }
    }
  }

  @Test
  fun `2 users exist`(
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
        userService.create(UserModel.Creator.noahFixture())
      }
      test {
        userService.listAll().map { it.sanitized() }
          .shouldContainExactlyInAnyOrder(
            UserModel.jeffFixture(),
            UserModel.noahFixture(),
          )
      }
    }
  }
}
