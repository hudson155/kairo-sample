package kairoSample.test

import kairo.sqlMigration.KairoSqlMigrationConfig
import kairo.sqlMigration.KairoSqlMigrationFeature

internal class KairoSampleTestKairoSqlMigrationFeature(
  schemas: List<String>,
) : KairoSqlMigrationFeature(
  KairoSqlMigrationConfig(
    run = true,
    cleanOnValidationError = true,
    locations = listOf("db/library/migration/common"),
    defaultSchema = "public",
    schemas = schemas,
    createSchemas = true,
    tableName = "database_migration",
  ),
)
