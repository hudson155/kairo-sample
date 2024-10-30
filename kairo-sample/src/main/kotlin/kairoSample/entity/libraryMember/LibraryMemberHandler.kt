package kairoSample.entity.libraryMember

import com.google.inject.Inject
import kairo.rest.auth.Auth
import kairo.rest.auth.AuthProvider
import kairo.rest.handler.RestHandler
import kairo.updater.update
import kairoSample.auth.superuser

internal class LibraryMemberHandler @Inject constructor(
  private val libraryMemberMapper: LibraryMemberMapper,
  private val libraryMemberService: LibraryMemberService,
) {
  internal inner class Get : RestHandler<LibraryMemberApi.Get, LibraryMemberRep>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryMemberApi.Get): Auth.Result =
      libraryMember(endpoint.libraryMemberId)

    override suspend fun handle(endpoint: LibraryMemberApi.Get): LibraryMemberRep {
      val libraryMemberId = endpoint.libraryMemberId
      val libraryMember = libraryMemberService.get(libraryMemberId) ?: throw LibraryMemberNotFound(libraryMemberId)
      return libraryMemberMapper.map(libraryMember)
    }
  }

  internal inner class GetByEmailAddress : RestHandler<LibraryMemberApi.GetByEmailAddress, LibraryMemberRep>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryMemberApi.GetByEmailAddress): Auth.Result {
      val libraryMemberId = libraryMemberService.getByEmailAddress(endpoint.emailAddress)?.id
      return libraryMember(libraryMemberId)
    }

    override suspend fun handle(endpoint: LibraryMemberApi.GetByEmailAddress): LibraryMemberRep {
      val emailAddress = endpoint.emailAddress
      val libraryMember = libraryMemberService.getByEmailAddress(emailAddress) ?: throw LibraryMemberNotFound(null)
      return libraryMemberMapper.map(libraryMember)
    }
  }

  internal inner class ListAll : RestHandler<LibraryMemberApi.ListAll, List<LibraryMemberRep>>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryMemberApi.ListAll): Auth.Result =
      superuser()

    override suspend fun handle(endpoint: LibraryMemberApi.ListAll): List<LibraryMemberRep> {
      val libraryMembers = libraryMemberService.listAll()
      return libraryMembers.map { libraryMemberMapper.map(it) }
    }
  }

  internal inner class Create : RestHandler<LibraryMemberApi.Create, LibraryMemberRep>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryMemberApi.Create): Auth.Result =
      superuser()

    override suspend fun handle(endpoint: LibraryMemberApi.Create): LibraryMemberRep {
      val body = endpoint.body
      val libraryMember = libraryMemberService.create(
        creator = libraryMemberMapper.map(body),
      )
      return libraryMemberMapper.map(libraryMember)
    }
  }

  internal inner class Update : RestHandler<LibraryMemberApi.Update, LibraryMemberRep>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryMemberApi.Update): Auth.Result =
      libraryMember(endpoint.libraryMemberId)

    override suspend fun handle(endpoint: LibraryMemberApi.Update): LibraryMemberRep {
      val libraryMemberId = endpoint.libraryMemberId
      val body = endpoint.body
      val libraryMember = libraryMemberService.update(libraryMemberId) { existing ->
        LibraryMemberModel.Update(
          emailAddress = update(existing.emailAddress, body.emailAddress),
          firstName = update(existing.firstName, body.firstName),
          lastName = update(existing.lastName, body.lastName),
        )
      }
      return libraryMemberMapper.map(libraryMember)
    }
  }

  internal inner class Delete : RestHandler<LibraryMemberApi.Delete, LibraryMemberRep>() {
    override suspend fun AuthProvider.auth(endpoint: LibraryMemberApi.Delete): Auth.Result =
      superuser()

    override suspend fun handle(endpoint: LibraryMemberApi.Delete): LibraryMemberRep {
      val libraryMemberId = endpoint.libraryMemberId
      val libraryMember = libraryMemberService.delete(libraryMemberId)
      return libraryMemberMapper.map(libraryMember)
    }
  }
}
