package kairoSample.library

import kairo.dependencyInjection.DependencyInjectionFeature
import kairo.id.IdFeature
import kairo.server.Server
import kairo.sql.SqlFeature
import kairoSample.Config
import kairoSample.loadConfig
import kairoSample.testing.ServerTest
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import org.koin.ksp.generated.kairosample_library_LibraryFeature

class LibraryFeatureTest : ServerTest {
  private val config: Config = loadConfig("testing/library-feature")

  private val koinApplication: KoinApplication =
    koinApplication {
      modules(kairosample_library_LibraryFeature)
    }

  override val koin: Koin = koinApplication.koin

  override val server: Server =
    Server(
      name = "Kairo Sample",
      features = listOf(
        DependencyInjectionFeature(koinApplication),
        IdFeature(config.id),
        LibraryFeature(koinApplication.koin),
        SqlFeature(
          config = config.sql,
          configureDatabase = {
            explicitDialect = PostgreSQLDialect()
          },
        ),
      ),
    )
}
