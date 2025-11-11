plugins {
  id("kairo-sample")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.add("-opt-in=org.jetbrains.exposed.v1.core.ExperimentalDatabaseMigrationApi")
  }
}

dependencies {
  implementation(project(":"))

  implementation(libs.exposedMigration.r2dbc)
  runtimeOnly(libs.postgres.r2dbc)
}

tasks.register<JavaExec>("generateMigrationScript") {
  classpath = sourceSets.main.get().runtimeClasspath
  mainClass = "kairoSample.db.GenerateMigrationScriptKt"
  systemProperty("scriptName", project.property("scriptName"))
}
