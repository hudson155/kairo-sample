plugins {
  kotlin("plugin.serialization")
  id("kairo-sample")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi")
    freeCompilerArgs.add("-opt-in=org.jetbrains.exposed.v1.core.ExperimentalDatabaseMigrationApi")
  }
}

dependencies {
  api(project(":"))

  api(libs.exposedMigration)
  api(libs.exposedMigration.jdbc)
  api(libs.flyway)
  api(libs.flyway.postgres)
  runtimeOnly(libs.postgres.jdbc)
  runtimeOnly(libs.postgresGcp.jdbc)
}

tasks.register<JavaExec>("generateMigrationScript") {
  classpath = sourceSets.main.get().runtimeClasspath
  mainClass = "kairoSample.db.GenerateMigrationScriptKt"
}

tasks.register<JavaExec>("runMigrations") {
  classpath = sourceSets.main.get().runtimeClasspath
  mainClass = "kairoSample.db.RunMigrationsKt"
}

tasks.register<JavaExec>("validateMigrationStatus") {
  classpath = sourceSets.main.get().runtimeClasspath
  mainClass = "kairoSample.db.ValidateMigrationStatusKt"
}
