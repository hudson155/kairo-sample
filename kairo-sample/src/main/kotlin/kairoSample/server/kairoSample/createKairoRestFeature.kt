package kairoSample.server.kairoSample

import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.plugins.cors.routing.CORS
import kairo.rest.KairoRestConfig
import kairo.rest.KairoRestFeature
import kairo.rest.auth.JwkJwtAuthMechanism
import kairo.rest.auth.JwtAuthVerifier
import kairo.rest.auth.kairoConfigure
import kairo.rest.cors.KairoCorsConfig
import kairo.rest.cors.kairoConfigure

internal fun createKairoRestFeature(
  config: KairoRestConfig,
  authConfig: KairoSampleServerConfig.Auth,
  corsConfig: KairoCorsConfig,
): KairoRestFeature =
  KairoRestFeature(config) {
    install(Authentication) {
      kairoConfigure {
        add(
          JwtAuthVerifier(
            schemes = listOf("Bearer"),
            mechanisms = listOf(
              JwkJwtAuthMechanism(
                issuers = listOf(authConfig.jwtIssuer),
                jwksEndpoint = authConfig.jwksEndpoint,
                leewaySec = authConfig.leewaySec,
              ),
            ),
          ),
        )
      }
    }
    install(CORS) {
      kairoConfigure(corsConfig)
    }
  }
