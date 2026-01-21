package kairoSample.chat.conversation

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import kairo.sql.PostgresExtension
import kairo.testing.setup
import kairo.testing.test
import kairoSample.chat.ChatFeatureTest
import kairoSample.identity.user.UserId
import kairoSample.identity.user.zero
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PostgresExtension::class, ChatFeatureTest::class)
class ListAllConversationsTest {
  private val userId: UserId = UserId.random()

  @Test
  fun `No conversations exist`(
    conversationService: ConversationService,
  ) {
    runTest {
      setup {
        repeat(2) {
          conversationService.create(
            creator = ConversationModel.Creator.fixture(userId = userId),
          )
        }
      }
      test {
        conversationService.listByUser(UserId.zero)
          .shouldBeEmpty()
      }
    }
  }

  @Test
  fun `2 conversations exist`(
    conversationService: ConversationService,
  ) {
    runTest {
      setup {
        repeat(2) {
          conversationService.create(
            creator = ConversationModel.Creator.fixture(userId = userId),
          )
        }
      }
      test {
        conversationService.listByUser(userId).map { it.sanitized() }
          .shouldContainExactlyInAnyOrder(
            List(2) { ConversationModel.fixture(userId = userId) },
          )
      }
    }
  }
}
