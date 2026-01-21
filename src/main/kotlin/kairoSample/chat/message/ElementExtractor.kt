package kairoSample.chat.message

import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.data.message.TextContent
import dev.langchain4j.data.message.ToolExecutionResultMessage
import dev.langchain4j.data.message.UserMessage
import org.koin.core.annotation.Single
import osiris.Agent
import osiris.ProvidesElements
import osiris.element.element.Element
import osiris.element.element.ParagraphElement
import osiris.element.parser.MarkdownParser

@Single
class ElementExtractor(
  private val agents: Map<String, Agent>,
) {
  suspend fun extract(author: MessageRep.Author, raw: ChatMessage): List<Element> {
    when (raw) {
      is AiMessage -> {
        author as MessageRep.Author.Agent
        val agent = checkNotNull(agents[author.name])
        val text = raw.text() ?: return emptyList()
        if (agent is ProvidesElements) return agent.elements(text)
        return listOf(ParagraphElement.text(text))
      }
      is ToolExecutionResultMessage -> {
        author as MessageRep.Author.Agent
        return emptyList()
      }
      is UserMessage -> {
        author as MessageRep.Author.User
        return raw.contents().flatMap { content ->
          when (content) {
            is TextContent -> MarkdownParser.parse(raw.singleText())
            else -> error("Unsupported content type: ${content::class.simpleName}.")
          }
        }
      }
      else -> {
        error("Unsupported message type: ${raw::class.simpleName}.")
      }
    }
  }
}
