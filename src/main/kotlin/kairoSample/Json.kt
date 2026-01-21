package kairoSample

import kairo.optional.OptionalModule
import kairo.rest.serialization.RestModule
import kairo.serialization.KairoJson
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.json.jsonb
import osiris.element.ElementModule

fun KairoJson.Builder.configure() {
  addModule(ElementModule())
  addModule(OptionalModule())
  addModule(RestModule())
}

val json: KairoJson =
  KairoJson {
    configure()
  }

inline fun <reified T : Any> Table.jsonb(name: String): Column<T> =
  jsonb(
    name = name,
    serialize = { json.serialize(it) },
    deserialize = { json.deserialize(it) },
  )
