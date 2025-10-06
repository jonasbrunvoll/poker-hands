package unit.services

import domain.Card
import domain.HandCategory
import domain.Rank
import domain.Suit
import org.junit.jupiter.api.*
import services.HandEvaluator

@DisplayName("HandEvaluatorUnitTest()")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // allows non-static @BeforeAll in Kotlin
@Tag("unit")
class HandEvaluatorUnitTest {

    private lateinit var hand: List<Card>

    @BeforeAll
    fun before_all() {
         hand = listOf(
            Card(Suit.SPADE, Rank.ACE),
            Card(Suit.CLUBS, Rank.ACE),
            Card(Suit.DIAMOND, Rank.ACE),
            Card(Suit.HEART, Rank.ACE),
            Card(Suit.SPADE, Rank.FIVE)
        )
    }

    @Test
    @DisplayName("evaluateHand_withNot_FOUR_OF_A_KIND_shouldFail()")
    fun evaluateHand_withNot_FOUR_OF_A_KIND_shouldFail() {
        // Setup
        val result = HandEvaluator.evaluateHand(handNumber = 1, hand = hand)

        // Assertion
        Assertions.assertTrue(result.isSuccess)
        Assertions.assertNotEquals(HandCategory.STRAIGHT, HandCategory.FOUR_OF_A_KIND)
    }

    @Test
    @DisplayName("evaluateHand_with_FOUR_OF_A_KIND_shouldPass()")
    fun evaluateHand_with_FOUR_OF_A_KIND_shouldPass() {
        // Setup
        val result = HandEvaluator.evaluateHand(handNumber = 1, hand = hand)

        // Assertion
        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals(HandCategory.FOUR_OF_A_KIND, HandCategory.FOUR_OF_A_KIND)
    }


}