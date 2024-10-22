package kairoSample.server.kairoSample

import kairo.clock.KairoClockFeature
import kairo.config.ConfigLoader
import kairo.googleAppEngine.KairoGoogleAppEngineFeature
import kairo.id.KairoIdFeature
import kairo.logging.KairoLoggingFeature
import kairo.server.FeatureManager
import kairo.server.Server
import kairo.sql.KairoSqlFeature
import kairo.sqlMigration.KairoSqlMigrationFeature
import kairoSample.feature.library.LibraryFeature

public class KairoSampleServer(
  config: KairoSampleServerConfig,
) : Server() {
  override val featureManager: FeatureManager =
    FeatureManager(
      features = setOf(
        KairoClockFeature(config.clock),
        KairoGoogleAppEngineFeature(),
        createKairoHealthCheckFeature(),
        KairoIdFeature(config.id),
        KairoLoggingFeature(config.logging),
        createKairoRestFeature(config.rest, config.auth, config.cors),
        KairoSqlFeature(config.sql),
        KairoSqlMigrationFeature(config.sqlMigration),
        LibraryFeature(),
      ),
      config = config.featureManager,
    )
}

internal fun main() {
  val config = ConfigLoader.createDefault().load<KairoSampleServerConfig>()
  val server = KairoSampleServer(config)
  server.start()
}
