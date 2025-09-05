package kairoSample.testing

import kotlin.time.Instant

// TODO: Should this go in Kairo instead?

public val Instant.Companion.epoch: Instant
  get() = fromEpochMilliseconds(0)
