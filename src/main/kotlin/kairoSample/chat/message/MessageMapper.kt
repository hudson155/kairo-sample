package kairoSample.chat.message

import dev.langchain4j.data.message.ChatMessageSerializer
import dev.langchain4j.data.message.UserMessage
import org.koin.core.annotation.Single
import kairoSample.json

@Single
class MessageMapper {
  fun creator(rep: MessageRep.Creator): MessageModel.Creator =
    MessageModel.Creator(
      conversationId = rep.conversationId,
      author = MessageRep.Author.User(
        userId = rep.userId,
      ),
      raw = UserMessage.from(rep.text),
    )

  fun rep(model: MessageModel): MessageRep =
    MessageRep(
      id = model.id,
      createdAt = model.createdAt,
      userId = model.userId,
      conversationId = model.conversationId,
      author = model.author,
      raw = json.deserialize(ChatMessageSerializer.messageToJson(model.raw)),
      elements = model.elements,
    )
}
