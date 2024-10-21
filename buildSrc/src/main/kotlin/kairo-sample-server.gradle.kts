plugins {
  application
  id("com.gradleup.shadow")
}

/**
 * See https://docs.gradle.org/current/userguide/working_with_files.html#sec:reproducible_archives
 */
tasks.withType<AbstractArchiveTask> {
  isPreserveFileTimestamps = false
  isReproducibleFileOrder = true
}

tasks.shadowJar {
  isZip64 = true
  archiveClassifier = "shadow"
  mergeServiceFiles()
}
