package kairoSample.db

import kairo.protectedString.ProtectedString
import kairoSample.chat.conversation.ConversationTable
import kairoSample.chat.message.MessageTable
import kairoSample.identity.user.UserTable
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.Database

@OptIn(ProtectedString.Access::class)
val database: Database by lazy {
  Database.connect(
    url = databaseConfig.url,
    user = databaseConfig.username,
    password = databaseConfig.password.value,
  )
}

val tables: List<Table> =
  listOf(
    ConversationTable,
    MessageTable,
    UserTable,
  )
