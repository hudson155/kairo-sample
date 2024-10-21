package kairoSample.feature.users

import kairo.featureTesting.KairoServerTest
import kairo.id.KairoIdConfig
import kairo.idTesting.TestKairoIdFeature
import kairo.restTesting.TestKairoRestFeature
import kairo.serverTesting.TestServer
import kairoSample.test.KairoSampleTestKairoSqlFeature
import kairoSample.test.KairoSampleTestKairoSqlMigrationFeature

internal abstract class UsersFeatureTest : KairoServerTest() {
  internal class Server : TestServer(
    featureUnderTest = UsersFeature(),
    supportingFeatures = setOf(
      TestKairoIdFeature(KairoIdConfig(KairoIdConfig.Generator.Deterministic)),
      TestKairoRestFeature(),
      KairoSampleTestKairoSqlFeature(),
      KairoSampleTestKairoSqlMigrationFeature(schemas = listOf("users")),
    ),
  )

  override val server: Server = Server()
}
