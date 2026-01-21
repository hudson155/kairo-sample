package kairoSample.chat.agent

import org.koin.core.annotation.Single
import osiris.Agent
import osiris.Context

@Single
class TestAgent : Agent("test") {
  var runs: Int = 0
    private set

  context(context: Context)
  override suspend fun executeAgent() {
    runs++
  }
}
