package kairoSample.entity.libraryBook

import kairo.featureTesting.Fixture

internal object LibraryBookFixture : Fixture<LibraryBookFixture.Instance>("libraryBook") {
  internal data class Instance(
    val creator: LibraryBookRep.Creator,
    val rep: LibraryBookRep,
  )

  val theNameOfTheWind: Instance = get("theNameOfTheWind")
  val arabianNights: Instance = get("arabianNights")
}
