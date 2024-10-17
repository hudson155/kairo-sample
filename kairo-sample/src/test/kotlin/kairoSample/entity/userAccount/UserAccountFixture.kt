package kairoSample.entity.userAccount

import kairo.featureTesting.Fixture

internal object UserAccountFixture : Fixture<UserAccountFixture.Instance>("userAccount") {
  internal data class Instance(
    val creator: UserAccountRep.Creator,
    val rep: UserAccountRep,
  )

  val jeff: Instance = get("jeff")
  val noah: Instance = get("noah")
}
