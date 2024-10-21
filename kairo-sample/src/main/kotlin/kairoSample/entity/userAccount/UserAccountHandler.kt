package kairoSample.entity.userAccount

import com.google.inject.Inject
import kairo.rest.auth.Auth
import kairo.rest.handler.RestHandler
import kairoSample.auth.superuser
import kairoSample.auth.userAccount

internal class UserAccountHandler @Inject constructor(
  private val userAccountMapper: UserAccountMapper,
  private val userAccountService: UserAccountService,
) {
  internal inner class Get : RestHandler<UserAccountApi.Get, UserAccountRep>() {
    override suspend fun Auth.auth(endpoint: UserAccountApi.Get): Auth.Result =
      userAccount(endpoint.userAccountId)

    override suspend fun handle(endpoint: UserAccountApi.Get): UserAccountRep {
      val (userAccountId) = endpoint
      val userAccount = userAccountService.get(userAccountId) ?: throw UserAccountNotFound()
      return userAccountMapper.map(userAccount)
    }
  }

  internal inner class Create : RestHandler<UserAccountApi.Create, UserAccountRep>() {
    override suspend fun Auth.auth(endpoint: UserAccountApi.Create): Auth.Result =
      superuser()

    override suspend fun handle(endpoint: UserAccountApi.Create): UserAccountRep {
      val (body) = endpoint
      val userAccount = userAccountService.create(
        creator = userAccountMapper.map(body),
      )
      return userAccountMapper.map(userAccount)
    }
  }
}
