package kairoSample.db

import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.migration.jdbc.MigrationUtils

fun main() {
  val statements = transaction(database) {
    MigrationUtils.statementsRequiredForDatabaseMigration(
      tables = tables.toTypedArray(),
    )
  }
  check(statements.isEmpty())
}
