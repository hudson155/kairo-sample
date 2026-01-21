package kairoSample.chat.conversation

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.matchers.shouldBe
import kairo.exception.shouldThrow
import kairo.sql.PostgresExtension
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kairoSample.chat.ChatFeatureTest
import kairoSample.chat.agent.AgentService
import kairoSample.chat.agent.TestAgent
import kairoSample.chat.conversation.exception.ConversationNotFound
import kairoSample.identity.user.UserId

@ExtendWith(PostgresExtension::class, ChatFeatureTest::class)
class ProcessConversationTest {
  private val userId: UserId = UserId.random()

  @Test
  fun `Conversation doesn't exist`(
    agentService: AgentService,
    conversationService: ConversationService,
    testAgent: TestAgent,
  ) {
    runTest {
      setup {
        conversationService.create(
          creator = ConversationModel.Creator.fixture(userId = userId),
        )
      }
      test {
        shouldThrow(ConversationNotFound.unprocessable(ConversationId.zero)) {
          agentService.process(ConversationId.zero)
        }
      }
      postcondition {
        testAgent.runs.shouldBe(0)
      }
    }
  }

  @Test
  fun `Happy path`(
    agentService: AgentService,
    conversationService: ConversationService,
    testAgent: TestAgent,
  ) {
    runTest {
      val conversation = setup {
        conversationService.create(
          creator = ConversationModel.Creator.fixture(userId = userId),
        )
      }
      test {
        shouldNotThrowAny {
          agentService.process(conversation.id)
        }
      }
      postcondition {
        testAgent.runs.shouldBe(1)
      }
    }
  }
}
