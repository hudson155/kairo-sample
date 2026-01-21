package kairoSample.chat.message

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import kairo.rest.HasRouting
import kairo.rest.route
import kairoSample.chat.agent.AgentService
import kairoSample.chat.message.exception.MessageNotFound
import org.koin.core.annotation.Single

@Single
class MessageHandler(
  private val agentService: AgentService,
  private val messageMapper: MessageMapper,
  private val messageService: MessageService,
) : HasRouting {
  @Suppress("LongMethod")
  override fun Application.routing() {
    routing {
      route(MessageApi.Get::class) {
        handle {
          val messageId = endpoint.messageId
          val message = messageService.get(messageId)
            ?: throw MessageNotFound(messageId)
          messageMapper.rep(message)
        }
      }

      route(MessageApi.ListByConversation::class) {
        handle {
          val messages = messageService.listByConversation(endpoint.conversationId)
          messages.map { messageMapper.rep(it) }
        }
      }

      route(MessageApi.Create::class) {
        handle {
          val message = messageService.create(
            creator = messageMapper.creator(endpoint.body),
          )
          agentService.process(message.conversationId)
          messageMapper.rep(message)
        }
      }

      route(MessageApi.Delete::class) {
        handle {
          val message = messageService.delete(endpoint.messageId)
          messageMapper.rep(message)
        }
      }
    }
  }
}
