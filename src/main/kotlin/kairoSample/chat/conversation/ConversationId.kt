package kairoSample.chat.conversation

import kairo.id.Id

@JvmInline
value class ConversationId(override val value: String) : Id {
  init {
    require(regex.matches(value)) { "Malformed conversation ID (value=$value). " }
  }

  companion object : Id.Companion<ConversationId>() {
    val regex: Regex = regex(prefix = Regex("convo"))

    override fun create(payload: String): ConversationId =
      ConversationId("convo_$payload")
  }
}
