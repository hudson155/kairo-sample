package kairoSample.server.kairoSample

import com.google.inject.Inject
import kairo.healthCheck.HealthCheck
import kairo.healthCheck.HealthCheckRep
import kairo.healthCheck.HealthCheckService
import kairo.sql.Sql
import org.jdbi.v3.core.kotlin.mapTo

internal class KairoSampleHealthCheckService @Inject constructor(
  private val sql: Sql,
) : HealthCheckService() {
  override val healthChecks: Map<String, HealthCheck> =
    mapOf(
      "server" to HealthCheck { serverHealthCheck() },
      "sql" to HealthCheck { sqlHealthCheck() },
    )

  private suspend fun sqlHealthCheck(): HealthCheckRep.Status =
    healthyIfNoException {
      val result = sql.sql { handle ->
        val query = handle.createQuery("select 1")
        return@sql query.mapTo<Int>().single()
      }
      check(result == 1)
    }
}
