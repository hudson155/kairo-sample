package kairoSample.chat.message

import io.kotest.matchers.shouldBe
import kairo.exception.shouldThrow
import kairo.sql.PostgresExtension
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kairoSample.chat.ChatFeatureTest
import kairoSample.chat.agent.TestAgent
import kairoSample.chat.conversation.ConversationId
import kairoSample.chat.conversation.ConversationModel
import kairoSample.chat.conversation.ConversationService
import kairoSample.chat.conversation.exception.ConversationNotFound
import kairoSample.chat.conversation.fixture
import kairoSample.chat.conversation.zero
import kairoSample.identity.user.UserId
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PostgresExtension::class, ChatFeatureTest::class)
class CreateMessageTest {
  private val userId: UserId = UserId.random()

  @Test
  fun `Message doesn't exist`(
    conversationService: ConversationService,
    messageService: MessageService,
  ) {
    runTest {
      setup {
        conversationService.create(
          creator = ConversationModel.Creator.fixture(userId = userId),
        )
      }
      test {
        shouldThrow(ConversationNotFound.unprocessable(ConversationId.zero)) {
          messageService.create(
            creator = MessageModel.Creator.fixture(
              userId = userId,
              conversationId = ConversationId.zero,
            ),
          )
        }
      }
    }
  }

  @Test
  fun `Happy path`(
    conversationService: ConversationService,
    messageService: MessageService,
    testAgent: TestAgent,
  ) {
    runTest {
      val conversation = setup {
        conversationService.create(
          creator = ConversationModel.Creator.fixture(userId = userId),
        )
      }
      val message = test {
        val message = messageService.create(
          creator = MessageModel.Creator.fixture(
            userId = userId,
            conversationId = conversation.id,
          ),
        )
        message.sanitized().shouldBe(
          MessageModel.fixture(
            userId = userId,
            conversationId = conversation.id,
          ),
        )
        return@test message
      }
      postcondition {
        messageService.get(message.id)
          .shouldBe(message)
        testAgent.runs.shouldBe(0)
      }
    }
  }
}
