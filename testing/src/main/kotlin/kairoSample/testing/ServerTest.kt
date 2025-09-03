package kairoSample.testing

import arrow.fx.coroutines.resourceScope
import kairo.server.Server
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.Koin

public interface ServerTest {
  public val koin: Koin
  public val server: Server

  @ServerTestDsl
  public fun restTest(block: suspend TestScope.() -> Unit) {
    runTest {
      resourceScope {
        install({ server.start() }, { _, _ -> server.stop() })
        block()
      }
    }
  }
}
