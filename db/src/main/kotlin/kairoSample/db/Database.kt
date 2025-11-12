package kairoSample.db

import kairo.protectedString.ProtectedString
import kairoSample.libraryBook.LibraryBookTable
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.Database

val tables: List<Table> =
  listOf(
    LibraryBookTable,
  )

@OptIn(ProtectedString.Access::class)
val database: Database by lazy {
  Database.connect(
    url = databaseConfig.url,
    user = databaseConfig.username,
    password = databaseConfig.password.value,
  )
}
