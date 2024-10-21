package kairoSample.feature.users

import com.google.inject.Binder
import kairo.feature.Feature
import kairo.feature.FeaturePriority
import kairo.rest.server.bindRestHandlers
import kairoSample.entity.userAccount.UserAccountHandler

public class UsersFeature : Feature() {
  override val name: String = "Users"

  override val priority: FeaturePriority = FeaturePriority.Normal

  override fun bind(binder: Binder) {
    binder.bindRestHandlers<UserAccountHandler>()
  }
}
