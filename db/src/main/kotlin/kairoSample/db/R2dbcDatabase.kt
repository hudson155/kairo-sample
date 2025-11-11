package kairoSample.db

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import kairo.protectedString.ProtectedString
import kairoSample.libraryBook.LibraryBookTable
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabaseConfig

val tables: List<Table> =
  listOf(
    LibraryBookTable,
  )

@OptIn(ProtectedString.Access::class)
val database: R2dbcDatabase by lazy {
  val options = ConnectionFactoryOptions.parse(databaseConfig.url).mutate().apply {
    option(ConnectionFactoryOptions.USER, databaseConfig.username)
    option(ConnectionFactoryOptions.PASSWORD, databaseConfig.password.value)
  }.build()
  return@lazy R2dbcDatabase.connect(
    connectionFactory = ConnectionFactories.get(options),
    databaseConfig = R2dbcDatabaseConfig {
      explicitDialect = PostgreSQLDialect()
    },
  )
}
