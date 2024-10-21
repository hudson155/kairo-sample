package kairoSample.test

import kairo.sql.KairoSqlConfig
import kairo.sqlTesting.TestKairoSqlFeature

internal class KairoSampleTestKairoSqlFeature : TestKairoSqlFeature(
  config = KairoSqlConfig(
    jdbcUrl = "jdbc:postgresql://localhost/kairo_sample_test",
    username = null,
    password = null,
    properties = emptyMap(),
    connectionTimeoutMs = 1000, // 1 second.
    minimumIdle = 4,
    maximumPoolSize = 16,
  ),
)
