package kairoSample.entity.userAccount

import com.google.inject.Inject
import kairo.sql.store.isUniqueViolation
import org.jdbi.v3.core.kotlin.bindKotlin
import org.postgresql.util.ServerErrorMessage
import kairoSample.common.KairoSampleSqlStore

internal class UserAccountStore @Inject constructor() : KairoSampleSqlStore<UserAccountModel>(
  tableName = "users.user_account",
) {
  suspend fun create(creator: UserAccountModel.Creator): UserAccountModel =
    sql { handle ->
      val query = handle.createQuery(rs("store/userAccount/create.sql"))
      query.bindKotlin(creator)
      return@sql query.mapToType().single()
    }

  override fun ServerErrorMessage.onError() {
    when {
      isUniqueViolation("uq__user_account__email_address") -> throw DuplicateEmailAddress()
    }
  }
}
