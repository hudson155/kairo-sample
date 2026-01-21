package kairoSample.chat.admin

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import kairo.coroutines.emitAll
import kairo.rest.Rest
import kairo.rest.RestEndpoint
import kairo.rest.route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import kairoSample.chat.conversation.ConversationId
import kairoSample.chat.conversation.ConversationService
import kairoSample.chat.message.MessageId
import kairoSample.chat.message.MessageService
import kairoSample.identity.user.UserId
import kairoSample.identity.user.UserService

@Single
class RecomputeElements(
  private val conversationService: ConversationService,
  private val messageService: MessageService,
  private val userService: UserService,
) : ChatAdmin() {
  data class Creator(
    val selection: Selection,
  ) {
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes(
      JsonSubTypes.Type(Selection.All::class, name = "All"),
      JsonSubTypes.Type(Selection.UserIds::class, name = "UserIds"),
      JsonSubTypes.Type(Selection.ConversationIds::class, name = "ConversationIds"),
      JsonSubTypes.Type(Selection.MessageIds::class, name = "MessageIds"),
    )
    sealed class Selection {
      data object All : Selection()

      data class UserIds(val userIds: List<UserId>) : Selection()

      data class ConversationIds(val conversationIds: List<ConversationId>) : Selection()

      data class MessageIds(val messageIds: List<MessageId>) : Selection()
    }
  }

  @Rest("POST", "/chat-admin/recompute-elements")
  @Rest.ContentType("application/json")
  @Rest.Accept("application/json")
  data class Endpoint(
    override val body: Creator,
  ) : RestEndpoint<Creator, Unit>()

  override fun Application.routing() {
    routing {
      route(Endpoint::class) {
        handle {
          messageIds(endpoint.body.selection).collect { messageId ->
            messageService.recomputeElements(messageId)
          }
        }
      }
    }
  }

  private fun messageIds(selection: Creator.Selection): Flow<MessageId> {
    fun conversationId(conversationId: ConversationId): Flow<MessageId> =
      flow { emitAll(messageService.listByConversation(conversationId)) }
        .map { it.id }

    fun userId(userId: UserId): Flow<MessageId> =
      flow { emitAll(conversationService.listByUser(userId)) }
        .flatMapConcat { conversationId(it.id) }

    return when (selection) {
      is Creator.Selection.All ->
        flow { emitAll(userService.listAll()) }
          .flatMapConcat { userId(it.id) }
      is Creator.Selection.UserIds ->
        selection.userIds.asFlow()
          .flatMapConcat { userId(it) }
      is Creator.Selection.ConversationIds ->
        selection.conversationIds.asFlow()
          .flatMapConcat { conversationId(it) }
      is Creator.Selection.MessageIds ->
        selection.messageIds.asFlow()
    }
  }
}
