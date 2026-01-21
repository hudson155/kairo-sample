package kairoSample.chat.message

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kairo.sql.PostgresExtension
import kairo.testing.setup
import kairo.testing.test
import kairoSample.chat.ChatFeatureTest
import kairoSample.chat.conversation.ConversationModel
import kairoSample.chat.conversation.ConversationService
import kairoSample.chat.conversation.fixture
import kairoSample.identity.user.UserId
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PostgresExtension::class, ChatFeatureTest::class)
class GetMessageTest {
  private val userId: UserId = UserId.random()

  @Test
  fun `Message doesn't exist`(
    conversationService: ConversationService,
    messageService: MessageService,
  ) {
    runTest {
      val conversation = setup {
        conversationService.create(
          creator = ConversationModel.Creator.fixture(userId = userId),
        )
      }
      setup {
        messageService.create(
          creator = MessageModel.Creator.fixture(
            userId = userId,
            conversationId = conversation.id,
          ),
        )
      }
      test {
        messageService.get(MessageId.zero)
          .shouldBeNull()
      }
    }
  }

  @Test
  fun `Message exists`(
    conversationService: ConversationService,
    messageService: MessageService,
  ) {
    runTest {
      val conversation = setup {
        conversationService.create(
          creator = ConversationModel.Creator.fixture(userId = userId),
        )
      }
      val message = setup {
        messageService.create(
          creator = MessageModel.Creator.fixture(
            userId = userId,
            conversationId = conversation.id,
          ),
        )
      }
      test {
        messageService.get(message.id)
          .shouldBe(message)
      }
    }
  }
}
