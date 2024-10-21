package kairoSample.server.kairoSample

import kairo.healthCheck.KairoHealthCheckFeature

internal fun createKairoHealthCheckFeature(): KairoHealthCheckFeature =
  KairoHealthCheckFeature(KairoSampleHealthCheckService::class)
