package kairoSample.chat.message

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import kairo.sql.PostgresExtension
import kairo.testing.setup
import kairo.testing.test
import kairoSample.chat.ChatFeatureTest
import kairoSample.chat.conversation.ConversationId
import kairoSample.chat.conversation.ConversationModel
import kairoSample.chat.conversation.ConversationService
import kairoSample.chat.conversation.fixture
import kairoSample.chat.conversation.zero
import kairoSample.identity.user.UserId
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PostgresExtension::class, ChatFeatureTest::class)
class ListMessagesByConversationTest {
  private val userId: UserId = UserId.random()

  @Test
  fun `No messages exist`(
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
        repeat(2) {
          messageService.create(
            creator = MessageModel.Creator.fixture(
              userId = userId,
              conversationId = conversation.id,
            ),
          )
        }
      }
      test {
        messageService.listByConversation(ConversationId.zero)
          .shouldBeEmpty()
      }
    }
  }

  @Test
  fun `2 messages exist`(
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
        repeat(2) {
          messageService.create(
            creator = MessageModel.Creator.fixture(
              userId = userId,
              conversationId = conversation.id,
            ),
          )
        }
      }
      test {
        messageService.listByConversation(conversation.id).map { it.sanitized() }
          .shouldContainExactlyInAnyOrder(
            List(2) {
              MessageModel.fixture(
                userId = userId,
                conversationId = conversation.id,
              )
            },
          )
      }
    }
  }
}
