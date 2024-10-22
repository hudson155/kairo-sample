package kairoSample.entity.libraryCard

import kairo.featureTesting.Fixture

internal object LibraryCardFixture : Fixture<LibraryCardFixture.Instance>("libraryCard") {
  internal data class Instance(
    val creator: LibraryCardRep.Creator,
    val rep: LibraryCardRep,
  )

  val jeff0: Instance = get("jeff0")
  val jeff1: Instance = get("jeff1")
}
