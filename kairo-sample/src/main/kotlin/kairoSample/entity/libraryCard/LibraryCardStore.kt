package kairoSample.entity.libraryCard

import com.google.inject.Inject
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kairo.exception.unprocessable
import kairo.id.KairoId
import kairoSample.common.KairoSampleSqlStore
import org.jdbi.v3.core.kotlin.bindKotlin

private val logger: KLogger = KotlinLogging.logger {}

internal class LibraryCardStore @Inject constructor() : KairoSampleSqlStore<LibraryCardModel>(
  tableName = "library.library_card",
) {
  suspend fun listAll(): List<LibraryCardModel> =
    sql { handle ->
      val query = handle.createQuery(rs("store/libraryCard/listAll.sql"))
      return@sql query.mapToType().toList()
    }

  suspend fun listByLibraryMember(libraryMemberId: KairoId): List<LibraryCardModel> =
    sql { handle ->
      val query = handle.createQuery(rs("store/libraryCard/listByLibraryMember.sql"))
      query.bind("libraryMemberId", libraryMemberId)
      return@sql query.mapToType().toList()
    }

  suspend fun create(creator: LibraryCardModel.Creator): LibraryCardModel =
    sql { handle ->
      logger.info { "Creating library card: $creator." }
      val query = handle.createQuery(rs("store/libraryCard/create.sql"))
      query.bindKotlin(creator)
      return@sql query.mapToType().single()
    }

  suspend fun delete(id: KairoId): LibraryCardModel =
    sql { handle ->
      logger.info { "Deleting library card: $id." }
      val query = handle.createQuery(rs("store/libraryCard/delete.sql"))
      query.bind("id", id)
      return@sql query.mapToType().singleNullOrThrow() ?: throw unprocessable(LibraryCardNotFound(id))
    }
}
