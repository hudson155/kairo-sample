package kairoSample.library

import kairo.dependencyInjection.DependencyInjectionFeature
import kairo.server.Server
import kairo.sql.SqlFeature
import kairo.sql.SqlFeatureConfig
import kairoSample.library.libraryBook.LibraryBookTable
import kairoSample.testing.get
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.v1.core.Schema
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication

// TODO: This file is still WIP. Perhaps extract a parent class for the testing Gradle module.

internal class LibraryFeatureTest : BeforeEachCallback, AfterEachCallback, ParameterResolver {
  override fun beforeEach(context: ExtensionContext) {
    runBlocking {
      val database = context.getStore(PerMethodDatabaseExtension.namespace).get<Database>("database")
      transaction(db = database) {
        SchemaUtils.createSchema(Schema("library"))
        SchemaUtils.create(
          LibraryBookTable,
        )
      }
      val koinApplication = koinApplication()
      context.getStore(namespace).put("koin", koinApplication.koin)
      val connectionFactory = context.getStore(PerMethodDatabaseExtension.namespace)
        .get<SqlFeatureConfig.ConnectionFactory>("connectionFactory")
        .let { checkNotNull(it) { "Library Feature tests require Postgres." } }
      val server = createServer(koinApplication, connectionFactory)
      context.getStore(namespace).put("server", server)
      server.start()
    }
  }

  private fun createServer(
    koinApplication: KoinApplication,
    connectionFactory: SqlFeatureConfig.ConnectionFactory,
  ): Server =
    Server(
      name = "Library Feature Test Server",
      features = listOf(
        DependencyInjectionFeature(koinApplication),
        LibraryFeature(koinApplication.koin),
        SqlFeature(
          config = SqlFeatureConfig(
            connectionFactory = connectionFactory,
            connectionPool = SqlFeatureConfig.ConnectionPool(
              size = SqlFeatureConfig.ConnectionPool.Size(initial = 2, min = 1, max = 5),
            ),
          ),
          configureDatabase = {
            explicitDialect = PostgreSQLDialect()
          },
        ),
      ),
    )

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
    @Suppress("UndeclaredKoinUsage")
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
    @Suppress("UndeclaredKoinUsage")
    return koin.get(kClass)
  }

  internal companion object {
    val namespace: ExtensionContext.Namespace =
      ExtensionContext.Namespace.create(LibraryFeatureTest::class)
  }
}
