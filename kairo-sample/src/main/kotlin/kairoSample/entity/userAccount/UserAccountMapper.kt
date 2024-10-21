package kairoSample.entity.userAccount

import com.google.inject.Inject
import kairo.id.KairoIdGenerator

internal class UserAccountMapper @Inject constructor(
  idGenerator: KairoIdGenerator.Factory,
) {
  private val idGenerator: KairoIdGenerator = idGenerator.withPrefix("user")

  internal fun map(model: UserAccountModel): UserAccountRep =
    UserAccountRep(
      id = model.id,
      emailAddress = model.emailAddress,
      firstName = model.firstName,
      lastName = model.lastName,
    )

  internal fun map(creator: UserAccountRep.Creator): UserAccountModel.Creator =
    UserAccountModel.Creator(
      id = idGenerator.generate(),
      emailAddress = creator.emailAddress,
      firstName = creator.firstName,
      lastName = creator.lastName,
    )
}
