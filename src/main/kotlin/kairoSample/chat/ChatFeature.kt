package kairoSample.chat

import io.ktor.server.application.Application
import kairo.dependencyInjection.HasKoinModules
import kairo.feature.Feature
import kairo.rest.HasRouting
import kairoSample.chat.admin.ChatAdmin
import kairoSample.chat.conversation.ConversationHandler
import kairoSample.chat.message.MessageHandler
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ksp.generated.module
import osiris.Agent

@org.koin.core.annotation.Module
@org.koin.core.annotation.ComponentScan
class ChatFeature(
  private val koin: Koin,
) : Feature(), HasKoinModules, HasRouting {
  override val name: String = "Chat"

  private val conversationHandler: ConversationHandler get() = koin.get()
  private val messageHandler: MessageHandler get() = koin.get()

  private val chatAdmin: List<ChatAdmin> get() = koin.getAll()

  override val koinModules: List<Module> =
    listOf(
      module,
      module {
        single<Map<String, Agent>> { getAll<Agent>().associateBy { agent -> agent.name } }
      },
    )

  override fun Application.routing() {
    with(conversationHandler) { routing() }
    with(messageHandler) { routing() }

    chatAdmin.forEach { with(it) { routing() } }
  }
}
