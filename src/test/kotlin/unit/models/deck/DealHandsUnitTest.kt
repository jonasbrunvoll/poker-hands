package unit.models.deck

import models.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("DealHandsUnitTest()")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // allows non-static @BeforeAll in Kotlin
@Tag("unit")
class DealHandsUnitTest {

    private lateinit var deck: List<Card>

    @BeforeAll
    fun set_up_deck() {
        deck = standard52Deck()
        assertEquals(52, deck.size)
    }

    @Test
    @DisplayName("throws when less than one hand is specified (numHands = 0)")
    fun requiresAtLeastOneHand__shouldFail() {
        val ex = assertThrows(IllegalArgumentException::class.java) {
            dealHands(deck = deck, numHands = 0, animate = false)
        }
        assertEquals("numHands must be >= 1", ex.message)
    }

    @Test
    @DisplayName("throws when hand size is less than one (handSize = 0)")
    fun requiresHandSizeOfAtLeastOne__shouldFail() {
        val ex = assertThrows(IllegalArgumentException::class.java) {
            dealHands(deck = deck, handSize = 0, animate = false)
        }
        assertEquals("handSize must be >= 1", ex.message)
    }
    @Test
    @DisplayName("throws when the deck is too small for the requested hands")
    fun throws_when_deck_is_too_small() {
        val tooSmallDeck = deck.take(9) // need at least 10 for 2 hands of 5
        val ex = assertThrows(IllegalArgumentException::class.java) {
            dealHands(deck = tooSmallDeck, numHands = 2, handSize = 5, animate = false)
        }
        assertTrue(ex.message!!.contains("Need at least 10 cards"))
    }
}