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

  api(libs.exposedMigration.r2dbc)
}

tasks.register<JavaExec>("generateMigrationScript") {
  classpath = sourceSets.main.get().runtimeClasspath
  mainClass = "kairoSample.db.GenerateMigrationScriptKt"
}
