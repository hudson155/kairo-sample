package kairoSample.testing

import arrow.fx.coroutines.resourceScope
import kairo.server.Server
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.Koin

interface ServerTest {
  val koin: Koin
  val server: Server

  @ServerTestDsl
  fun restTest(block: suspend TestScope.() -> Unit) {
    runTest {
      resourceScope {
        install({ server.start() }, { _, _ -> server.stop() })
        block()
      }
    }
  }
}
