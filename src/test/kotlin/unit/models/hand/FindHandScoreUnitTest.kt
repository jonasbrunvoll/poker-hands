package unit.models

import models.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("findHandScore()")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // allows non-static @BeforeAll in Kotlin
@Tag("unit")
class FindHandScoreUnitTest {

    @BeforeAll
    fun before_all() {
        // e.g., expensive setup shared by all tests
    }

    @BeforeEach
    fun before_each() {
        // e.g., reset state
    }

    @Test
    @DisplayName("throws when not exactly five cards")
    fun throws_if_not_exactly_five_cards() {
        val fourCards = listOf(
            Card(Suit.CLUBS, Rank.KING),
            Card(Suit.CLUBS, Rank.QUEEN),
            Card(Suit.CLUBS, Rank.JACK),
            Card(Suit.CLUBS, Rank.TEN),
        )

        val ex = assertThrows(IllegalArgumentException::class.java) {
            findHandScore(fourCards)
        }
        assertTrue(ex.message!!.contains("Expected exactly 5 cards. Currently, ${fourCards.size} are provided"))
    }
}