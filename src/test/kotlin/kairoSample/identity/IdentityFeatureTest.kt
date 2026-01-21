package kairoSample.identity

import io.mockk.mockk
import kairo.dependencyInjection.DependencyInjectionFeature
import kairo.feature.FeatureTest
import kairo.server.Server
import kairo.sql.PostgresExtensionAware
import kairo.sql.SqlFeature
import kairo.sql.from
import kairo.stytch.StytchFeature
import kairoSample.identity.user.UserTable
import org.jetbrains.exposed.v1.core.Schema
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.core.KoinApplication

class IdentityFeatureTest : FeatureTest(), PostgresExtensionAware {
  override fun beforeEach(context: ExtensionContext) {
    transaction(db = checkNotNull(context.database)) {
      SchemaUtils.createSchema(Schema("identity"))
      SchemaUtils.create(
        UserTable,
      )
    }
    super.beforeEach(context)
  }

  override fun createServer(context: ExtensionContext, koinApplication: KoinApplication): Server =
    Server(
      name = "Identity Feature Test Server",
      features = listOf(
        DependencyInjectionFeature(koinApplication),
        IdentityFeature(koinApplication.koin),
        SqlFeature.from(checkNotNull(context.connectionFactory)),
        StytchFeature(lazy { mockk(relaxed = true) }),
      ),
    )
}
