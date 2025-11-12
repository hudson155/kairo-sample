package kairoSample.db

import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.migration.jdbc.MigrationUtils

fun main() {
  transaction(database) {
    MigrationUtils.generateMigrationScript(
      tables = tables.toTypedArray(),
      scriptDirectory = "src/main/resources/migration",
      scriptName = "V0__generated",
    )
  }
}
