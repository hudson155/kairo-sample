package kairoSample.entity.userAccount

import com.google.inject.Inject
import kairo.id.KairoId

internal class UserAccountService @Inject constructor(
  private val userAccountStore: UserAccountStore,
) {
  suspend fun get(id: KairoId): UserAccountModel? =
    userAccountStore.get(id)

  suspend fun create(creator: UserAccountModel.Creator): UserAccountModel =
    userAccountStore.create(creator)
}
