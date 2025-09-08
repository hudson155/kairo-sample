package kairoSample.library

import kairo.dependencyInjection.DependencyInjectionFeature
import kairo.server.Server
import kairo.sql.SqlFeature
import kairoSample.library.libraryBook.LibraryBookTable
import kairoSample.testing.FeatureTest
import kairoSample.testing.PostgresExtensionAware
import kairoSample.testing.from
import org.jetbrains.exposed.v1.core.Schema
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.core.KoinApplication

internal class LibraryFeatureTest : FeatureTest(), PostgresExtensionAware {
  override fun beforeEach(context: ExtensionContext) {
    transaction(db = checkNotNull(context.database)) {
      SchemaUtils.createSchema(Schema("library"))
      SchemaUtils.create(
        LibraryBookTable,
      )
    }
    super.beforeEach(context)
  }

  override fun createServer(context: ExtensionContext, koinApplication: KoinApplication): Server =
    Server(
      name = "Library Feature Test Server",
      features = listOf(
        DependencyInjectionFeature(koinApplication),
        LibraryFeature(koinApplication.koin),
        SqlFeature.from(checkNotNull(context.connectionFactory)),
      ),
    )
}
