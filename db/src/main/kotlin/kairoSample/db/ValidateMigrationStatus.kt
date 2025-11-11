package kairoSample.db

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.v1.migration.r2dbc.MigrationUtils
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

fun main() {
  runBlocking {
    val statements = suspendTransaction(database) {
      MigrationUtils.statementsRequiredForDatabaseMigration(
        tables = tables.toTypedArray(),
      )
    }
    check(statements.isEmpty())
  }
}
