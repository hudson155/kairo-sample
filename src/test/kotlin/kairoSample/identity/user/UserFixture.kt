package kairoSample.identity.user

import kairo.datetime.epoch
import kotlin.time.Instant

val UserId.Companion.zero: UserId
  get() = UserId("user_00000000")

fun UserModel.sanitized(): UserModel =
  copy(
    id = UserId.zero,
    createdAt = Instant.epoch,
  )

fun UserModel.Creator.Companion.jeffFixture(): UserModel.Creator =
  UserModel.Creator(
    firstName = "Jeff",
    lastName = "Hudson",
    emailAddress = "jeff.hudson@airborne.software",
  )

fun UserModel.Companion.jeffFixture(): UserModel =
  UserModel(
    id = UserId.zero,
    createdAt = Instant.epoch,
    firstName = "Jeff",
    lastName = "Hudson",
    emailAddress = "jeff.hudson@airborne.software",
  )

fun UserModel.Creator.Companion.noahFixture(): UserModel.Creator =
  UserModel.Creator(
    firstName = "Noah",
    lastName = "Guld",
    emailAddress = "noah.guld@airborne.software",
  )

fun UserModel.Companion.noahFixture(): UserModel =
  UserModel(
    id = UserId.zero,
    createdAt = Instant.epoch,
    firstName = "Noah",
    lastName = "Guld",
    emailAddress = "noah.guld@airborne.software",
  )
