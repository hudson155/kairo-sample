package kairoSample.identity.user

import kotlin.time.Instant
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp

object UserTable : Table("identity.user_account") {
  val id: Column<UserId> =
    text("id")
      .transform(::UserId, UserId::value)

  override val primaryKey: PrimaryKey = PrimaryKey(id)

  val createdAt: Column<Instant> =
    timestamp("created_at")
      .defaultExpression(CurrentTimestamp)

  val version: Column<Long> =
    long("version")
      .default(0)

  val updatedAt: Column<Instant> =
    timestamp("updated_at")
      .defaultExpression(CurrentTimestamp)

  val firstName: Column<String?> =
    text("first_name")
      .nullable()

  val lastName: Column<String?> =
    text("last_name")
      .nullable()

  // TODO: Make this unique.
  val emailAddress: Column<String> =
    text("email_address")
}

fun UserModel.Companion.fromRow(row: ResultRow): UserModel =
  UserModel(
    id = row[UserTable.id],
    createdAt = row[UserTable.createdAt],
    firstName = row[UserTable.firstName],
    lastName = row[UserTable.lastName],
    emailAddress = row[UserTable.emailAddress],
  )
