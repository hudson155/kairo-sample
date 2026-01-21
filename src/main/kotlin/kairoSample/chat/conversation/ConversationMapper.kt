package kairoSample.chat.conversation

import org.koin.core.annotation.Single

@Single
class ConversationMapper {
  fun creator(rep: ConversationRep.Creator): ConversationModel.Creator =
    ConversationModel.Creator(
      userId = rep.userId,
      agentName = rep.agentName,
      processing = false,
    )

  fun rep(model: ConversationModel): ConversationRep =
    ConversationRep(
      id = model.id,
      createdAt = model.createdAt,
      userId = model.userId,
      agentName = model.agentName,
      state = ConversationRep.State(
        canSendMessage = !model.processing,
      ),
    )
}
