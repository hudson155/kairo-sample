import com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer

plugins {
  application
  id("com.gradleup.shadow")
}

tasks.shadowJar {
  isZip64 = true
  archiveClassifier = "shadow"
  mergeServiceFiles()
  transform(Log4j2PluginsCacheFileTransformer())
}
