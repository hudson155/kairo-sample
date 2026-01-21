package kairoSample.chat.message

import kairo.id.Id

@JvmInline
value class MessageId(override val value: String) : Id {
  init {
    require(regex.matches(value)) { "Malformed message ID (value=$value). " }
  }

  companion object : Id.Companion<MessageId>() {
    val regex: Regex = regex(prefix = Regex("msg"))

    override fun create(payload: String): MessageId =
      MessageId("msg_$payload")
  }
}
