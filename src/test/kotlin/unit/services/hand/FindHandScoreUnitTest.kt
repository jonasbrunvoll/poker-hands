package unit.services.hand

import domain.*
import org.junit.jupiter.api.*
import services.HandEvaluator

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

    /*

    @Test
    @DisplayName("throws when not exactly five cards")
    fun throws_if_not_exactly_five_cards() {
        val fourCards = listOf(
            Card(Suit.CLUBS, Rank.KING),
            Card(Suit.CLUBS, Rank.QUEEN),
            Card(Suit.CLUBS, Rank.JACK),
            Card(Suit.CLUBS, Rank.TEN),
        )

        assertThrows<IllegalArgumentException> {
            HandEvaluator.evaluateHand(fourCards)
        }
    }


     */

}