package kairoSample.db

import kairoSample.libraryBook.LibraryBookTable
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase

val tables: List<Table> =
  listOf(
    LibraryBookTable,
  )

@Suppress("ForbiddenMethodCall")
val database: R2dbcDatabase =
  R2dbcDatabase.connect(
    url = requireNotNull(System.getenv("KAIRO_SAMPLE_POSTGRES_URL")),
    user = requireNotNull(System.getenv("KAIRO_SAMPLE_POSTGRES_USERNAME")),
    password = requireNotNull(System.getenv("KAIRO_SAMPLE_POSTGRES_PASSWORD")),
  )
