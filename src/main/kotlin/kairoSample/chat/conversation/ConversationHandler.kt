package kairoSample.chat.conversation

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import kairo.rest.HasRouting
import kairo.rest.route
import org.koin.core.annotation.Single
import kairoSample.chat.agent.AgentService
import kairoSample.chat.conversation.exception.ConversationNotFound

@Single
class ConversationHandler(
  private val agentService: AgentService,
  private val conversationMapper: ConversationMapper,
  private val conversationService: ConversationService,
) : HasRouting {
  @Suppress("LongMethod")
  override fun Application.routing() {
    routing {
      route(ConversationApi.Get::class) {
        handle {
          val conversationId = endpoint.conversationId
          val conversation = conversationService.get(conversationId)
            ?: throw ConversationNotFound(conversationId)
          conversationMapper.rep(conversation)
        }
      }

      route(ConversationApi.ListByUser::class) {
        handle {
          val conversations = conversationService.listByUser(endpoint.userId)
          conversations.map { conversationMapper.rep(it) }
        }
      }

      route(ConversationApi.Create::class) {
        handle {
          val conversation = conversationService.create(
            creator = conversationMapper.creator(endpoint.body),
          )
          agentService.process(conversation.id)
          conversationMapper.rep(conversation)
        }
      }

      route(ConversationApi.Delete::class) {
        handle {
          val conversation = conversationService.delete(endpoint.conversationId)
          conversationMapper.rep(conversation)
        }
      }
    }
  }
}
