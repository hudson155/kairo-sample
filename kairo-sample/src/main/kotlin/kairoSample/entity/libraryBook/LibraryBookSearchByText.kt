package kairoSample.entity.libraryBook

import kairoSample.exception.InvalidQueryCombination

internal data class LibraryBookSearchByText(
  val title: String? = null,
  val author: String? = null,
) {
  init {
    if (title == null && author == null) {
      throw InvalidQueryCombination("title or author must be provided.")
    }
  }
}
