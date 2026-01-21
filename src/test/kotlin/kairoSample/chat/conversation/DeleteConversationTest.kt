package kairoSample.chat.conversation

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kairo.exception.shouldThrow
import kairo.sql.PostgresExtension
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kairoSample.chat.ChatFeatureTest
import kairoSample.chat.conversation.exception.ConversationNotFound
import kairoSample.identity.user.UserId
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PostgresExtension::class, ChatFeatureTest::class)
class DeleteConversationTest {
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
        shouldThrow(ConversationNotFound.unprocessable(ConversationId.zero)) {
          conversationService.delete(ConversationId.zero)
        }
      }
    }
  }

  @Test
  fun `Happy path`(
    conversationService: ConversationService,
  ) {
    runTest {
      val conversation = setup {
        conversationService.create(
          creator = ConversationModel.Creator.fixture(userId = userId),
        )
      }
      test {
        conversationService.delete(conversation.id)
          .shouldBe(conversation)
      }
      postcondition {
        conversationService.get(conversation.id)
          .shouldBeNull()
      }
    }
  }
}
