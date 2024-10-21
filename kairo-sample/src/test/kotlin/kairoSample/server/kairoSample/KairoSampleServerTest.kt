package kairoSample.server.kairoSample

import kairo.config.ConfigLoader
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

/**
 * This test merely ensures that [KairoSampleServer] starts up successfully.
 * There are no assertions; the test passes if no exceptions are thrown.
 */
internal class KairoSampleServerTest {
  @Suppress("TestMethodWithoutAssertion")
  @Test
  fun test(): Unit = runTest {
    val config = ConfigLoader.createTesting().load<KairoSampleServerConfig>("test")
    val server = KairoSampleServer(config)

    server.start(wait = false)
    server.shutDown()
  }
}
