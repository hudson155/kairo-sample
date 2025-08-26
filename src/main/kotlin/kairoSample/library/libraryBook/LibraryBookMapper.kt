package kairoSample.library.libraryBook

import org.koin.core.annotation.Single

@Single
class LibraryBookMapper {
  fun creator(rep: LibraryBookRep.Creator): LibraryBookModel.Creator =
    LibraryBookModel.Creator(
      title = rep.title,
      authors = rep.authors,
      isbn = rep.isbn,
    )

  fun rep(model: LibraryBookModel): LibraryBookRep =
    LibraryBookRep(
      id = model.id,
      createdAt = model.createdAt,
      title = model.title,
      authors = model.authors,
      isbn = model.isbn,
    )
}
