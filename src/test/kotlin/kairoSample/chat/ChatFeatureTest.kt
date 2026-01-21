package kairoSample.chat

import kairo.dependencyInjection.DependencyInjectionFeature
import kairo.feature.FeatureTest
import kairo.server.Server
import kairo.sql.PostgresExtensionAware
import kairo.sql.SqlFeature
import kairo.sql.from
import kairoSample.chat.conversation.ConversationTable
import kairoSample.chat.message.MessageTable
import org.jetbrains.exposed.v1.core.Schema
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.core.KoinApplication

class ChatFeatureTest : FeatureTest(), PostgresExtensionAware {
  override fun beforeEach(context: ExtensionContext) {
    transaction(db = checkNotNull(context.database)) {
      SchemaUtils.createSchema(Schema("chat"))
      SchemaUtils.create(
        ConversationTable,
        MessageTable,
      )
    }
    super.beforeEach(context)
  }

  override fun createServer(context: ExtensionContext, koinApplication: KoinApplication): Server =
    Server(
      name = "Chat Feature Test Server",
      features = listOf(
        ChatFeature(koinApplication.koin),
        ChatTestFeature(),
        DependencyInjectionFeature(koinApplication),
        SqlFeature.from(checkNotNull(context.connectionFactory)),
      ),
    )
}
