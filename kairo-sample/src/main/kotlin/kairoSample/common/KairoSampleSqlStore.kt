package kairoSample.common

import kairo.id.KairoId
import kairo.sql.store.SqlStore

internal abstract class KairoSampleSqlStore<T : KairoSampleModel>(
  private val tableName: String,
) : SqlStore.ForType<T>() {
  suspend fun get(id: KairoId): T? =
    get(id, forUpdate = false)

  protected suspend fun get(id: KairoId, forUpdate: Boolean): T? =
    sql { handle ->
      val query = handle.createQuery(rs("store/sql/get.sql"))
      query.define("tableName", tableName)
      query.define("lockingClause", if (forUpdate) "for no key update" else "")
      query.bind("id", id)
      return@sql query.mapToType().singleNullOrThrow()
    }

  protected suspend fun <U : Any> update(id: KairoId, updater: (model: T) -> U): U? {
    val model = get(id, forUpdate = true) ?: return null
    return updater(model)
  }
}
