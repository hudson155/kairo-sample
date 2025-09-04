package kairoSample.library

import kairo.dependencyInjection.DependencyInjectionFeature
import kairo.id.IdFeature
import kairo.protectedString.ProtectedString
import kairo.server.Server
import kairo.sql.SqlFeature
import kairo.sql.SqlFeatureConfig
import kairoSample.testing.get
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import org.koin.ksp.generated.kairosample_library_LibraryFeature

private val namespace: ExtensionContext.Namespace =
  ExtensionContext.Namespace.create(LibraryFeatureTest::class)

@OptIn(ProtectedString.Access::class) // TODO: Remove this.
class LibraryFeatureTest : BeforeEachCallback, AfterEachCallback, ParameterResolver {
  override fun beforeEach(context: ExtensionContext) {
    runBlocking {
      val koinApplication = createKoinApplication()
      context.getStore(namespace).put("koin", koinApplication.koin)
      val server = createServer(koinApplication)
      context.getStore(namespace).put("server", server)
      server.start()
    }
  }

  private fun createKoinApplication(): KoinApplication =
    koinApplication {
      modules(kairosample_library_LibraryFeature)
    }

  private fun createServer(koinApplication: KoinApplication): Server {
    val libraryFeature = LibraryFeature(koinApplication.koin)
    return Server(
      name = "${libraryFeature.name} Feature Test Server",
      features = listOf(
        DependencyInjectionFeature(koinApplication),
        IdFeature(), // TODO: Use deterministic generation.
        libraryFeature,
        SqlFeature(
          config = SqlFeatureConfig(
            connectionFactory = SqlFeatureConfig.ConnectionFactory(
              url = "r2dbc:postgresql://localhost:5432/kairo_sample", // TODO: Use testcontainers.
              username = "highbeam",
              password = ProtectedString("highbeam"),
            ),
          ),
          configureDatabase = {
            explicitDialect = PostgreSQLDialect()
          },
        ),
      ),
    )
  }

  override fun afterEach(context: ExtensionContext): Unit =
    runBlocking {
      context.getStore(namespace).get<Server>("server")?.let { server ->
        server.stop()
      }
    }

  override fun supportsParameter(
    parameterContext: ParameterContext,
    extensionContext: ExtensionContext,
  ): Boolean {
    val kClass = parameterContext.parameter.type.kotlin
    when (kClass) {
      Koin::class -> return true
      Server::class -> return true
    }
    val koin = checkNotNull(extensionContext.getStore(namespace).get<Koin>("koin"))
    return koin.getOrNull<Any>(kClass) != null
  }

  override fun resolveParameter(
    parameterContext: ParameterContext,
    extensionContext: ExtensionContext,
  ): Any {
    val kClass = parameterContext.parameter.type.kotlin
    when (kClass) {
      Koin::class -> return checkNotNull(extensionContext.getStore(namespace).get<Koin>("koin"))
      Server::class -> return checkNotNull(extensionContext.getStore(namespace).get<Server>("server"))
    }
    val koin = checkNotNull(extensionContext.getStore(namespace).get<Koin>("koin"))
    return koin.get(kClass)
  }
}
