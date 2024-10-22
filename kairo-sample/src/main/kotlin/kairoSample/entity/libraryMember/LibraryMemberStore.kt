package kairoSample.entity.libraryMember

import com.google.inject.Inject
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kairo.exception.unprocessable
import kairo.id.KairoId
import kairo.sql.store.isUniqueViolation
import kairo.updater.Updater
import kairoSample.common.KairoSampleSqlStore
import org.jdbi.v3.core.kotlin.bindKotlin
import org.postgresql.util.ServerErrorMessage

private val logger: KLogger = KotlinLogging.logger {}

internal class LibraryMemberStore @Inject constructor() : KairoSampleSqlStore<LibraryMemberModel>(
  tableName = "library.library_member",
) {
  suspend fun getByEmailAddress(emailAddress: String): LibraryMemberModel? =
    sql { handle ->
      val query = handle.createQuery(rs("store/libraryMember/getByEmailAddress.sql"))
      query.bind("emailAddress", emailAddress)
      return@sql query.mapToType().singleNullOrThrow()
    }

  suspend fun listAll(): List<LibraryMemberModel> =
    sql { handle ->
      val query = handle.createQuery(rs("store/libraryMember/listAll.sql"))
      return@sql query.mapToType().toList()
    }

  suspend fun create(creator: LibraryMemberModel.Creator): LibraryMemberModel =
    sql { handle ->
      logger.info { "Creating library member: $creator." }
      val query = handle.createQuery(rs("store/libraryMember/create.sql"))
      query.bindKotlin(creator)
      return@sql query.mapToType().single()
    }

  suspend fun update(
    id: KairoId,
    updater: Updater<LibraryMemberModel.Update>,
  ): LibraryMemberModel =
    sql { handle ->
      val libraryMember = get(id, forUpdate = true) ?: throw unprocessable(LibraryMemberNotFound(id))
      val update = updater.update(LibraryMemberModel.Update(libraryMember))
      logger.info { "Updating library member: $update." }
      val query = handle.createQuery(rs("store/libraryMember/update.sql"))
      query.bind("id", id)
      query.bindKotlin(update)
      return@sql query.mapToType().single()
    }

  suspend fun delete(id: KairoId): LibraryMemberModel =
    sql { handle ->
      val query = handle.createQuery(rs("store/libraryMember/delete.sql"))
      query.bind("id", id)
      return@sql query.mapToType().singleNullOrThrow() ?: throw unprocessable(LibraryMemberNotFound(id))
    }

  override fun ServerErrorMessage.onError() {
    when {
      isUniqueViolation("uq__library_member__email_address") -> throw DuplicateLibraryMemberEmailAddress()
    }
  }
}
