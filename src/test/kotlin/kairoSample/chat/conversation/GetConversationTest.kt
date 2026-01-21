package kairoSample.chat.conversation

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kairo.sql.PostgresExtension
import kairo.testing.setup
import kairo.testing.test
import kairoSample.chat.ChatFeatureTest
import kairoSample.identity.user.UserId
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PostgresExtension::class, ChatFeatureTest::class)
class GetConversationTest {
  private val userId: UserId = UserId.random()

  @Test
  fun `Conversation doesn't exist`(
    conversationService: ConversationService,
  ) {
    runTest {
      setup {
        conversationService.create(
          creator = ConversationModel.Creator.fixture(userId = userId),
        )
      }
      test {
        conversationService.get(ConversationId.zero)
          .shouldBeNull()
      }
    }
  }

  @Test
  fun `Conversation exists`(
    conversationService: ConversationService,
  ) {
    runTest {
      val conversation = setup {
        conversationService.create(
          creator = ConversationModel.Creator.fixture(userId = userId),
        )
      }
      test {
        conversationService.get(conversation.id)
          .shouldBe(conversation)
      }
    }
  }
}
