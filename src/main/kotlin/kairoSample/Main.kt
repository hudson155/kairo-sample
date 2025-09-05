package kairoSample

import io.ktor.server.application.install
import io.ktor.server.engine.applicationEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

fun main() {
  runBlocking {
    val database = createDatabase()
    val ktorServer = createKtorServer(database)
    ktorServer.start()
    Thread.sleep(Long.MAX_VALUE)
  }
}

private fun createDatabase() =
  R2dbcDatabase.connect(
    url = "r2dbc:postgresql://localhost:5432/kairo_sample",
    user = "highbeam",
    password = "highbeam",
  )

private fun createKtorServer(database: R2dbcDatabase) =
  embeddedServer(
    factory = Netty,
    environment = applicationEnvironment(),
    configure = {
      connector {
        host = "0.0.0.0"
        port = 8080
      }
    },
    module = {
      install(CallLogging) {
        disableDefaultColors()
      }
      routing {
        get("/library-books") {
          suspendTransaction(db = database) {
            LibraryBookTable.selectAll().toList()
          }
          call.respond("It worked!")
        }
      }
    },
  )
