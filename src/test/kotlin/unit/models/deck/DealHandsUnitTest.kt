package unit.models.deck

import models.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("DealHandsUnitTest()")
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
    @DisplayName("dealHandsResult_withTooFewHands_shouldFail()")
    fun dealHandsResult_withTooFewHands_shouldFail() {
        // Setup
        val result = dealHandsResult(deck = deck, numHands = 0, animate = false)

        // Assertion
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    @DisplayName("dealHandsResult_withTooSmallHandSize_shouldFail()")
    fun dealHandsResult_withTooSmallHandSize_shouldFail() {
        // Setup
        val result =  dealHandsResult(deck = deck, handSize = 0, animate = false)

        // Assertion
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }
    @Test
    @DisplayName("dealHandsResult_withTooSmallDeck__ShouldFail()")
    fun dealHandsResult_withTooSmallDeck__ShouldFail() {
        // Setup
        val tooSmallDeck = deck.take(9) // need at least 10 for 2 hands of 5
        val result = dealHandsResult(deck = tooSmallDeck, numHands = 2, handSize = 5, animate = false)

        // Assertion
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    @DisplayName("dealHandsResult_withTooSmallDeck__ShouldSucceed()")
    fun dealHandsResult_withTooSmallDeck__ShouldSucceed(){
        // Setup
        val result = dealHandsResult(deck = deck, numHands = 2, handSize = 5, animate = false)

        // Assertion
        assertTrue(result.isSuccess)
    }
}