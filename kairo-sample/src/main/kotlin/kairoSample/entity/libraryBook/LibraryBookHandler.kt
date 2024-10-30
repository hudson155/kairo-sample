package kairoSample.entity.libraryBook

import com.google.inject.Inject
import kairo.rest.auth.Auth
import kairo.rest.auth.AuthProvider
import kairo.rest.auth.public
import kairo.rest.handler.RestHandler
import kairo.updater.update
import kairoSample.auth.superuser

internal class LibraryBookHandler @Inject constructor(
  private val libraryBookMapper: LibraryBookMapper,
  private val libraryBookService: LibraryBookService,
) {
  internal inner class Get : RestHandler<LibraryBookApi.Get, LibraryBookRep>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryBookApi.Get): Auth.Result =
      public()

    override suspend fun handle(endpoint: LibraryBookApi.Get): LibraryBookRep {
      val libraryBookId = endpoint.libraryBookId
      val libraryBook = libraryBookService.get(libraryBookId) ?: throw LibraryBookNotFound(libraryBookId)
      return libraryBookMapper.map(libraryBook)
    }
  }

  internal inner class SearchByIsbn : RestHandler<LibraryBookApi.GetByIsbn, LibraryBookRep>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryBookApi.GetByIsbn): Auth.Result =
      public()

    override suspend fun handle(endpoint: LibraryBookApi.GetByIsbn): LibraryBookRep {
      val isbn = endpoint.isbn
      val libraryBook = libraryBookService.getByIsbn(isbn) ?: throw LibraryBookNotFound(null)
      return libraryBookMapper.map(libraryBook)
    }
  }

  internal inner class ListAll : RestHandler<LibraryBookApi.ListAll, List<LibraryBookRep>>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryBookApi.ListAll): Auth.Result =
      public()

    override suspend fun handle(endpoint: LibraryBookApi.ListAll): List<LibraryBookRep> {
      val libraryBooks = libraryBookService.listAll()
      return libraryBooks.map { libraryBookMapper.map(it) }
    }
  }

  internal inner class SearchByText : RestHandler<LibraryBookApi.SearchByText, List<LibraryBookRep>>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryBookApi.SearchByText): Auth.Result =
      public()

    override suspend fun handle(endpoint: LibraryBookApi.SearchByText): List<LibraryBookRep> {
      val title = endpoint.title
      val author = endpoint.author
      val libraryBooks = libraryBookService.searchByText(title = title, author = author)
      return libraryBooks.map { libraryBookMapper.map(it) }
    }
  }

  internal inner class Create : RestHandler<LibraryBookApi.Create, LibraryBookRep>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryBookApi.Create): Auth.Result =
      superuser()

    override suspend fun handle(endpoint: LibraryBookApi.Create): LibraryBookRep {
      val body = endpoint.body
      val libraryBook = libraryBookService.create(
        creator = libraryBookMapper.map(body),
      )
      return libraryBookMapper.map(libraryBook)
    }
  }

  internal inner class Update : RestHandler<LibraryBookApi.Update, LibraryBookRep>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryBookApi.Update): Auth.Result =
      superuser()

    override suspend fun handle(endpoint: LibraryBookApi.Update): LibraryBookRep {
      val libraryBookId = endpoint.libraryBookId
      val body = endpoint.body
      val libraryBook = libraryBookService.update(libraryBookId) { existing ->
        LibraryBookModel.Update(
          title = update(existing.title, body.title),
          author = update(existing.author, body.author),
        )
      }
      return libraryBookMapper.map(libraryBook)
    }
  }

  internal inner class Delete : RestHandler<LibraryBookApi.Delete, LibraryBookRep>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryBookApi.Delete): Auth.Result =
      superuser()

    override suspend fun handle(endpoint: LibraryBookApi.Delete): LibraryBookRep {
      val libraryBookId = endpoint.libraryBookId
      val libraryBook = libraryBookService.delete(libraryBookId)
      return libraryBookMapper.map(libraryBook)
    }
  }
}
