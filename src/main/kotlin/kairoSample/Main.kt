package kairoSample

import kairo.feature.Feature
import kairo.server.Server

internal fun main() {
  val features = emptyList<Feature>()
  val server = Server(features)
  server.start()
}
