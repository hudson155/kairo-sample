package kairoSample.entity.libraryCard

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import kairo.restTesting.client
import kairo.testing.setup
import kairo.testing.test
import kairoSample.entity.libraryMember.LibraryMemberFixture
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryCardListByLibraryMemberTest : LibraryFeatureTest() {
  @Test
  fun empty(): Unit = runTest {
    test("List by Jeff") {
      client.request(LibraryCardApi.ListByLibraryMember(LibraryMemberFixture.jeff.rep.id))
        .shouldBeEmpty()
    }
  }

  @Test
  fun `non-empty`(): Unit = runTest {
    setup("Create Jeff 0") {
      val creator = LibraryCardFixture.jeff0.creator
      client.request(LibraryCardApi.Create(creator))
    }

    setup("Create Jeff 1") {
      val creator = LibraryCardFixture.jeff1.creator
      client.request(LibraryCardApi.Create(creator))
    }

    test("List by Jeff") {
      client.request(LibraryCardApi.ListByLibraryMember(LibraryMemberFixture.jeff.rep.id))
        .shouldContainExactlyInAnyOrder(
          LibraryCardFixture.jeff0.rep,
          LibraryCardFixture.jeff1.rep,
        )
    }
  }
}
