package kairoSample.entity.libraryMember

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.util.Optional
import kairo.exception.UnprocessableException
import kairo.restTesting.client
import kairo.testing.postcondition
import kairo.testing.setup
import kairo.testing.test
import kairoSample.feature.library.LibraryFeatureTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class LibraryMemberUpdateTest : LibraryFeatureTest() {
  private val emptyUpdate: LibraryMemberRep.Update =
    LibraryMemberRep.Update()

  private val completeUpdate: LibraryMemberRep.Update =
    LibraryMemberRep.Update(
      emailAddress = " NEW@JHUDSON.CA ",
      firstName = Optional.of("New"),
      lastName = Optional.empty(),
    )

  override suspend fun beforeEach() {
    setup("Create Jeff") {
      val creator = LibraryMemberFixture.jeff.creator
      client.request(LibraryMemberApi.Create(creator))
    }
  }

  @Test
  fun `does not exist`(): Unit = runTest {
    test("Update Noah") {
      shouldThrow<UnprocessableException> {
        client.request(LibraryMemberApi.Update(LibraryMemberFixture.noah.rep.id, completeUpdate))
      }.e.shouldBeInstanceOf<LibraryMemberNotFound>()
    }

    postcondition("Get Noah") {
      shouldThrow<LibraryMemberNotFound> {
        client.request(LibraryMemberApi.Get(LibraryMemberFixture.noah.rep.id))
      }
    }
  }

  @Test
  fun `happy path (empty)`(): Unit = runTest {
    val jeff = LibraryMemberFixture.jeff.rep

    test("Update Jeff") {
      client.request(LibraryMemberApi.Update(LibraryMemberFixture.jeff.rep.id, emptyUpdate))
        .shouldBe(jeff)
    }
  }

  @Test
  fun `happy path (complete)`(): Unit = runTest {
    val jeff = LibraryMemberFixture.jeff.rep.copy(
      emailAddress = "new@jhudson.ca",
      firstName = "New",
      lastName = null,
    )

    test("Update Jeff") {
      client.request(LibraryMemberApi.Update(LibraryMemberFixture.jeff.rep.id, completeUpdate))
        .shouldBe(jeff)
    }

    postcondition("Get Jeff") {
      client.request(LibraryMemberApi.Get(LibraryMemberFixture.jeff.rep.id))
        .shouldBe(jeff)
    }
  }
}
