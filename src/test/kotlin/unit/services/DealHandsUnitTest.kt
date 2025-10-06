package unit.services

import domain.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import services.Dealer

@DisplayName("DealerUnitTest()")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
class DealHandsUnitTest {

    private lateinit var deck: List<Card>

    @BeforeAll
    fun set_up_deck() {
        deck = standard52Deck()
        assertEquals(52, deck.size)
    }

    @Test
    @DisplayName("deal_withTooFewHands_shouldFail()")
    fun dealt_withTooFewHands_shouldFail() {
        // Setup
        val result = Dealer.deal(deck = deck, numHands = 0, animate = false)

        // Assertion
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    @DisplayName("deal_withTooSmallHandSize_shouldFail()")
    fun deal_withTooSmallHandSize_shouldFail() {
        // Setup
        val result = Dealer.deal(deck = deck, handSize = 0, animate = false)

        // Assertion
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }
    @Test
    @DisplayName("deal_withTooSmallDeck__ShouldFail()")
    fun deal_withTooSmallDeck__ShouldFail() {
        // Setup
        val tooSmallDeck = deck.take(9) // need at least 10 for 2 hands of 5
        val result = Dealer.deal(deck = tooSmallDeck, numHands = 2, handSize = 5, animate = false)

        // Assertion
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    @DisplayName("deal_withTooSmallDeck__ShouldSucceed()")
    fun deal_withTooSmallDeck__ShouldSucceed(){
        // Setup
        val result = Dealer.deal(deck = deck, numHands = 2, handSize = 5, animate = false)

        // Assertion
        assertTrue(result.isSuccess)
    }
}