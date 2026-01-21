package kairoSample.chat.agent.exception

import io.ktor.http.HttpStatusCode
import kairo.exception.LogicalFailure

class AgentNotFound(
  val agentName: String,
) : LogicalFailure("Agent not found") {
  override val type: String = "AgentNotFound"
  override val status: HttpStatusCode = HttpStatusCode.UnprocessableEntity

  override fun MutableMap<String, Any?>.buildJson() {
    put("agentName", agentName)
  }
}
