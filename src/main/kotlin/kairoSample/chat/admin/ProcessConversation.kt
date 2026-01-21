package kairoSample.chat.admin

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import kairo.rest.Rest
import kairo.rest.RestEndpoint
import kairo.rest.route
import org.koin.core.annotation.Single
import kairoSample.chat.agent.AgentService
import kairoSample.chat.conversation.ConversationId

@Single
class ProcessConversation(
  private val agentService: AgentService,
) : ChatAdmin() {
  data class Creator(
    val conversationId: ConversationId,
  )

  @Rest("POST", "/chat-admin/process-conversation")
  @Rest.ContentType("application/json")
  @Rest.Accept("application/json")
  data class Endpoint(
    override val body: Creator,
  ) : RestEndpoint<Creator, Unit>()

  override fun Application.routing() {
    routing {
      route(Endpoint::class) {
        handle {
          agentService.process(endpoint.body.conversationId)
        }
      }
    }
  }
}
