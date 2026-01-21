package kairoSample.db

import kairo.protectedString.ProtectedString
import org.flywaydb.core.Flyway

@OptIn(ProtectedString.Access::class)
private val flyway: Flyway by lazy {
  Flyway.configure().apply {
    dataSource(databaseConfig.url, databaseConfig.username, databaseConfig.password.value)
    table("database_migration")
    defaultSchema("public")
    schemas(
      "chat",
      "identity",
    )
    locations("classpath:migration")
    validateOnMigrate(true)
    cleanOnValidationError(false)
    cleanDisabled(true)
    createSchemas(true)
  }.load()
}

fun main() {
  flyway.migrate()
}
