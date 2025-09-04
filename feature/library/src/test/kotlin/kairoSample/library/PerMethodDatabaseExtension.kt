package kairoSample.library

import java.sql.DriverManager
import kairo.protectedString.ProtectedString
import kairo.sql.SqlFeatureConfig
import kairoSample.testing.get
import kotlin.uuid.Uuid
import org.jetbrains.exposed.v1.jdbc.Database
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.PostgreSQLContainer

// TODO: This file is still WIP. Perhaps extract a parent class for the testing Gradle module.
// TODO: Move this somewhere shared. Either the testing Gradle module or Kairo.

internal class PerMethodDatabaseExtension : BeforeAllCallback, BeforeEachCallback, AfterEachCallback {
  override fun beforeAll(context: ExtensionContext) {
    context.root.getStore(namespace).getOrComputeIfAbsent("postgres") {
      val postgres = PostgreSQLContainer("postgres:16.9")
      postgres.start()
      return@getOrComputeIfAbsent postgres
    }
  }

  @Suppress("SqlSourceToSinkFlow")
  @OptIn(ProtectedString.Access::class)
  override fun beforeEach(context: ExtensionContext) {
    val postgres = checkNotNull(context.root.getStore(namespace).get<PostgreSQLContainer<*>>("postgres"))
    val databaseName = Uuid.random().toString()
    context.getStore(namespace).put("databaseName", databaseName)
    DriverManager.getConnection(postgres.jdbcUrl, postgres.username, postgres.password).use { connection ->
      connection.createStatement().executeUpdate("create database \"$databaseName\"")
    }
    context.getStore(namespace).put(
      "database",
      Database.connect(
        url = postgres.jdbcUrl.replace("/${postgres.databaseName}", "/$databaseName"),
        user = postgres.username,
        password = postgres.password,
      ),
    )
    context.getStore(namespace).put(
      "connectionFactory",
      SqlFeatureConfig.ConnectionFactory(
        url = postgres.jdbcUrl.replace("jdbc:", "r2dbc:").replace("/${postgres.databaseName}", "/$databaseName"),
        username = postgres.username,
        password = postgres.password.let { ProtectedString(it) },
      ),
    )
  }

  override fun afterEach(context: ExtensionContext) {
    val postgres = checkNotNull(context.root.getStore(namespace).get<PostgreSQLContainer<*>>("postgres"))
    val databaseName = context.getStore(namespace).get<String>("databaseName")
    DriverManager.getConnection(postgres.jdbcUrl, postgres.username, postgres.password).use { connection ->
      connection.createStatement().executeUpdate("drop database if exists \"$databaseName\"")
    }
  }

  internal companion object {
    val namespace: ExtensionContext.Namespace =
      ExtensionContext.Namespace.create(PerMethodDatabaseExtension::class)
  }
}
