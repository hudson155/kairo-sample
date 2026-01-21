package kairoSample.chat.agent

import org.koin.core.annotation.Single
import osiris.Agent
import kairoSample.chat.agent.exception.AgentNotFound
import kairoSample.chat.conversation.ConversationId
import kairoSample.chat.conversation.ConversationService
import kairoSample.chat.conversation.exception.ConversationNotFound

@Single
class AgentService(
  private val agents: Map<String, Agent>,
  private val contextFactory: ContextFactory,
  conversationService: Lazy<ConversationService>,
) {
  private val conversationService: ConversationService by conversationService

  suspend fun process(conversationId: ConversationId) {
    val conversation = conversationService.get(conversationId)
      ?: throw ConversationNotFound.unprocessable(conversationId)
    val agentName = conversation.agentName
    val agent = agents[agentName] ?: throw AgentNotFound(agentName)
    val context = contextFactory.create(conversation)
    agent.execute(context)
  }
}
