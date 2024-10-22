package kairoSample.entity.libraryBook

import com.google.inject.Inject
import kairo.id.KairoIdGenerator

internal class LibraryBookMapper @Inject constructor(
  idGenerator: KairoIdGenerator.Factory,
) {
  private val idGenerator: KairoIdGenerator = idGenerator.withPrefix("library_book")

  internal fun map(model: LibraryBookModel): LibraryBookRep =
    LibraryBookRep(
      id = model.id,
      title = model.title,
      author = model.author,
      isbn = model.isbn,
    )

  internal fun map(creator: LibraryBookRep.Creator): LibraryBookModel.Creator =
    LibraryBookModel.Creator(
      id = idGenerator.generate(),
      title = creator.title,
      author = creator.author,
      isbn = creator.isbn,
    )
}
