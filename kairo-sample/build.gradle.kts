plugins {
  id("kairo-sample")
  id("kairo-sample-server")
}

dependencies {
  implementation(project(":db:library"))

  api(libs.kairoClockFeature)
  api(libs.kairoConfig)
  api(libs.kairoFeature)
  api(libs.kairoHealthCheckFeature)
  api(libs.kairoIdFeature)
  api(libs.kairoLoggingFeature)
  api(libs.kairoRestFeature)
  implementation(libs.kairoRestFeatureCors)
  api(libs.kairoServer)
  api(libs.kairoSqlFeature)
  api(libs.kairoSqlMigrationFeature)

  testImplementation(libs.kairoFeatureTesting)
  testImplementation(libs.kairoIdFeatureTesting)
  testImplementation(libs.kairoRestFeatureTesting)
  testImplementation(libs.kairoSqlFeatureTesting)
  testImplementation(libs.kairoTesting)
}

application {
  mainClass = "kairoSample.server.kairoSample.KairoSampleServerKt"
}
