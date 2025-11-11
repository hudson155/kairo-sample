package kairoSample.db

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.v1.migration.r2dbc.MigrationUtils
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

fun main() {
  val scriptName = requireNotNull(System.getProperty("scriptName"))
  runBlocking {
    suspendTransaction(database) {
      MigrationUtils.generateMigrationScript(
        tables = tables.toTypedArray(),
        scriptDirectory = "src/main/resources/migration",
        scriptName = scriptName,
      )
    }
  }
}
