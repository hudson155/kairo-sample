package kairoSample.libraryBook

import io.ktor.resources.Resource

@Resource("/library-books")
data object LibraryBookApi {
  @Resource("{id}")
  data class Id(
    val parent: LibraryBookApi = LibraryBookApi,
    val id: LibraryBookId,
  )
}
