package kairoSample.auth

import kairo.rest.auth.JwtPrincipal
import kairo.rest.auth.Principal

@Suppress("UseIfInsteadOfWhen")
internal fun <T> Principal.principal(
  jwt: JwtPrincipal.() -> T,
): T =
  when (this) {
    is JwtPrincipal -> jwt()
    else -> error("Unrecognized principal: ${this::class.simpleName!!}.")
  }
