package kairoSample.feature.library

import kairo.featureTesting.KairoServerTest
import kairo.id.KairoIdConfig
import kairo.idTesting.TestKairoIdFeature
import kairo.restTesting.TestKairoRestFeature
import kairo.serverTesting.TestServer
import kairoSample.test.KairoSampleTestKairoSqlFeature
import kairoSample.test.KairoSampleTestKairoSqlMigrationFeature

internal abstract class LibraryFeatureTest : KairoServerTest() {
  internal class Server : TestServer(
    featureUnderTest = LibraryFeature(),
    supportingFeatures = setOf(
      TestKairoIdFeature(KairoIdConfig(KairoIdConfig.Generator.Deterministic)),
      TestKairoRestFeature(),
      KairoSampleTestKairoSqlFeature(),
      KairoSampleTestKairoSqlMigrationFeature(schemas = listOf("library")),
    ),
  )

  override val server: Server = Server()
}
