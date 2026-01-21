package kairoSample.chat.conversation

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.koin.core.annotation.Single
import osiris.Agent
import kairoSample.chat.agent.exception.AgentNotFound
import kairoSample.identity.user.UserId

private val logger: KLogger = KotlinLogging.logger {}

@Single
class ConversationService(
  private val agents: Map<String, Agent>,
  private val conversationStore: ConversationStore,
) {
  suspend fun get(id: ConversationId): ConversationModel? =
    conversationStore.get(id)

  suspend fun listByUser(userId: UserId): List<ConversationModel> =
    conversationStore.listByUser(userId)

  suspend fun create(creator: ConversationModel.Creator): ConversationModel {
    logger.info { "Creating conversation (creator=$creator)." }
    val agentName = creator.agentName
    if (agentName !in agents) throw AgentNotFound(agentName)
    return conversationStore.create(creator)
  }

  suspend fun delete(id: ConversationId): ConversationModel {
    logger.info { "Deleting conversation (id=$id)." }
    return conversationStore.delete(id)
  }
}
