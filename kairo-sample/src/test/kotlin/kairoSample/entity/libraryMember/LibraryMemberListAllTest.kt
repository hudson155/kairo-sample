package kairoSample.entity.libraryMember

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import kairo.restTesting.client
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryMemberListAllTest : LibraryFeatureTest() {
  @Test
  fun empty(): Unit = runTest {
    test("List all") {
      client.request(LibraryMemberApi.ListAll)
        .shouldBeEmpty()
    }
  }

  @Test
  fun `non-empty`(): Unit = runTest {
    setup("Create Jeff") {
      val creator = LibraryMemberFixture.jeff.creator
      client.request(LibraryMemberApi.Create(creator))
    }

    setup("Create Noah") {
      val creator = LibraryMemberFixture.noah.creator
      client.request(LibraryMemberApi.Create(creator))
    }

    test("List all") {
      client.request(LibraryMemberApi.ListAll)
        .shouldContainExactlyInAnyOrder(
          LibraryMemberFixture.jeff.rep,
          LibraryMemberFixture.noah.rep,
        )
    }
  }
}
