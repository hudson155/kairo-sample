package kairoSample.entity.libraryCard

import com.google.inject.Inject
import kairo.rest.auth.Auth
import kairo.rest.handler.RestHandler
import kairoSample.auth.LibraryCardAuthProvider
import kairoSample.auth.libraryMember
import kairoSample.auth.superuser

internal class LibraryCardHandler @Inject constructor(
  private val libraryCardAuthProvider: LibraryCardAuthProvider,
  private val libraryCardMapper: LibraryCardMapper,
  private val libraryCardService: LibraryCardService,
) {
  internal inner class Get : RestHandler<LibraryCardApi.Get, LibraryCardRep>() {
    override suspend fun Auth.auth(endpoint: LibraryCardApi.Get): Auth.Result =
      with(libraryCardAuthProvider) {
        libraryCard(endpoint.libraryCardId)
      }

    override suspend fun handle(endpoint: LibraryCardApi.Get): LibraryCardRep {
      val libraryCardId = endpoint.libraryCardId
      val libraryCard = libraryCardService.get(libraryCardId) ?: throw LibraryCardNotFound(libraryCardId)
      return libraryCardMapper.map(libraryCard)
    }
  }

  internal inner class ListAll : RestHandler<LibraryCardApi.ListAll, List<LibraryCardRep>>() {
    override suspend fun Auth.auth(endpoint: LibraryCardApi.ListAll): Auth.Result =
      superuser()

    override suspend fun handle(endpoint: LibraryCardApi.ListAll): List<LibraryCardRep> {
      val libraryCards = libraryCardService.listAll()
      return libraryCards.map { libraryCardMapper.map(it) }
    }
  }

  internal inner class ListByLibraryMember : RestHandler<LibraryCardApi.ListByLibraryMember, List<LibraryCardRep>>() {
    override suspend fun Auth.auth(endpoint: LibraryCardApi.ListByLibraryMember): Auth.Result =
      libraryMember(endpoint.libraryMemberId)

    override suspend fun handle(endpoint: LibraryCardApi.ListByLibraryMember): List<LibraryCardRep> {
      val libraryMemberId = endpoint.libraryMemberId
      val libraryCards = libraryCardService.listByLibraryMember(libraryMemberId)
      return libraryCards.map { libraryCardMapper.map(it) }
    }
  }

  internal inner class Create : RestHandler<LibraryCardApi.Create, LibraryCardRep>() {
    override suspend fun Auth.auth(endpoint: LibraryCardApi.Create): Auth.Result =
      superuser()

    override suspend fun handle(endpoint: LibraryCardApi.Create): LibraryCardRep {
      val body = endpoint.body
      val libraryCard = libraryCardService.create(
        creator = libraryCardMapper.map(body),
      )
      return libraryCardMapper.map(libraryCard)
    }
  }

  internal inner class Delete : RestHandler<LibraryCardApi.Delete, LibraryCardRep>() {
    override suspend fun Auth.auth(endpoint: LibraryCardApi.Delete): Auth.Result =
      superuser()

    override suspend fun handle(endpoint: LibraryCardApi.Delete): LibraryCardRep {
      val libraryCardId = endpoint.libraryCardId
      val libraryCard = libraryCardService.delete(libraryCardId)
      return libraryCardMapper.map(libraryCard)
    }
  }
}
