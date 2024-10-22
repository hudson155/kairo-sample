package kairoSample.entity.libraryMember

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kairo.exception.UnprocessableException
import kairo.restTesting.client
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryMemberDeleteTest : LibraryFeatureTest() {
  override suspend fun beforeEach() {
    setup("Create Jeff") {
      val creator = LibraryMemberFixture.jeff.creator
      client.request(LibraryMemberApi.Create(creator))
    }
  }

  @Test
  fun `does not exist`(): Unit = runTest {
    test("Delete Noah") {
      shouldThrow<UnprocessableException> {
        client.request(LibraryMemberApi.Delete(LibraryMemberFixture.noah.rep.id))
      }.e.shouldBeInstanceOf<LibraryMemberNotFound>()
    }
  }

  @Test
  fun `happy path`(): Unit = runTest {
    test("Delete Jeff") {
      client.request(LibraryMemberApi.Delete(LibraryMemberFixture.jeff.rep.id))
        .shouldBe(LibraryMemberFixture.jeff.rep)
    }

    postcondition("Get Jeff") {
      shouldThrow<LibraryMemberNotFound> {
        client.request(LibraryMemberApi.Get(LibraryMemberFixture.jeff.rep.id))
      }
    }
  }
}
