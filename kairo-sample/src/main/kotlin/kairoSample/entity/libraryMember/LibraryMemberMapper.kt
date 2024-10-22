package kairoSample.entity.libraryMember

import com.google.inject.Inject
import kairo.id.KairoIdGenerator

internal class LibraryMemberMapper @Inject constructor(
  idGenerator: KairoIdGenerator.Factory,
) {
  private val idGenerator: KairoIdGenerator = idGenerator.withPrefix("library_member")

  internal fun map(model: LibraryMemberModel): LibraryMemberRep =
    LibraryMemberRep(
      id = model.id,
      emailAddress = model.emailAddress,
      firstName = model.firstName,
      lastName = model.lastName,
    )

  internal fun map(creator: LibraryMemberRep.Creator): LibraryMemberModel.Creator =
    LibraryMemberModel.Creator(
      id = idGenerator.generate(),
      emailAddress = creator.emailAddress,
      firstName = creator.firstName,
      lastName = creator.lastName,
    )
}
