package kairoSample.testing

import kairo.server.Server
import kairoSample.testing.PostgresExtension.Companion.namespace
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.core.Koin

public interface FeatureTestAware {
  public var ExtensionContext.koin: Koin?
    get() = getStore(namespace).get<Koin>("koin")
    set(value) {
      getStore(namespace).put("koin", value)
    }

  public var ExtensionContext.server: Server?
    get() = getStore(namespace).get<Server>("server")
    set(value) {
      getStore(namespace).put("server", value)
    }
}
