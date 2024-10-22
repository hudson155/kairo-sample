package kairoSample.feature.library

import com.google.inject.Binder
import kairo.feature.Feature
import kairo.feature.FeaturePriority
import kairo.rest.server.bindRestHandlers
import kairoSample.entity.libraryBook.LibraryBookHandler
import kairoSample.entity.libraryCard.LibraryCardHandler
import kairoSample.entity.libraryMember.LibraryMemberHandler

public class LibraryFeature : Feature() {
  override val name: String = "Library"

  override val priority: FeaturePriority = FeaturePriority.Normal

  override fun bind(binder: Binder) {
    binder.bindRestHandlers<LibraryBookHandler>()
    binder.bindRestHandlers<LibraryCardHandler>()
    binder.bindRestHandlers<LibraryMemberHandler>()
  }
}
