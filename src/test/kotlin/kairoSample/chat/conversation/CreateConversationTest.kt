package kairoSample.chat.conversation

import io.kotest.matchers.shouldBe
import kairo.sql.PostgresExtension
import kairo.testing.postcondition
import kairo.testing.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kairoSample.chat.ChatFeatureTest
import kairoSample.chat.agent.TestAgent
import kairoSample.identity.user.UserId

@ExtendWith(PostgresExtension::class, ChatFeatureTest::class)
class CreateConversationTest {
  private val userId: UserId = UserId.random()

  @Test
  fun `Happy path`(
    conversationService: ConversationService,
    testAgent: TestAgent,
  ) {
    runTest {
      val conversation = test {
        val conversation = conversationService.create(
          creator = ConversationModel.Creator.fixture(userId = userId),
        )
        conversation.sanitized().shouldBe(ConversationModel.fixture(userId = userId))
        return@test conversation
      }
      postcondition {
        conversationService.get(conversation.id)
          .shouldBe(conversation)
        testAgent.runs.shouldBe(0)
      }
    }
  }
}
