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

fun UserModel.Creator.Companion.fixture(): UserModel.Creator =
  UserModel.Creator(
    firstName = "Jeff",
    lastName = "Hudson",
    emailAddress = "jeff.hudson@airborne.software",
  )

fun UserModel.Companion.fixture(): UserModel =
  UserModel(
    id = UserId.zero,
    createdAt = Instant.epoch,
    firstName = "Jeff",
    lastName = "Hudson",
    emailAddress = "jeff.hudson@airborne.software",
  )
