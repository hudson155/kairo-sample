package kairoSample.identity.user

import org.koin.core.annotation.Single

@Single
class UserMapper {
  fun creator(rep: UserRep.Creator): UserModel.Creator =
    UserModel.Creator(
      firstName = rep.firstName,
      lastName = rep.lastName,
      emailAddress = rep.emailAddress,
    )

  fun update(rep: UserRep.Update): UserModel.Update =
    UserModel.Update(
      firstName = rep.firstName,
      lastName = rep.lastName,
    )

  fun rep(model: UserModel): UserRep =
    UserRep(
      id = model.id,
      createdAt = model.createdAt,
      firstName = model.firstName,
      lastName = model.lastName,
      emailAddress = model.emailAddress,
    )
}
