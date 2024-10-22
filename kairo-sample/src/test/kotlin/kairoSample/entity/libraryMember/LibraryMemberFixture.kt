package kairoSample.entity.libraryMember

import kairo.featureTesting.Fixture

internal object LibraryMemberFixture : Fixture<LibraryMemberFixture.Instance>("libraryMember") {
  internal data class Instance(
    val creator: LibraryMemberRep.Creator,
    val rep: LibraryMemberRep,
  )

  val jeff: Instance = get("jeff")
  val noah: Instance = get("noah")
}
