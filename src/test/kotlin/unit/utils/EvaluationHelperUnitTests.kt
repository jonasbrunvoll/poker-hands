package unit.utils

import domain.Card
import domain.Rank
import domain.Suit
import org.junit.jupiter.api.*
import utils.cardsByRankDesc
import utils.countByRank
import utils.isFlush
import utils.ranksByCountThenRankDesc

@DisplayName("EvaluationHelperUnitTests()")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // allows non-static @BeforeAll in Kotlin
@Tag("unit")
class EvaluationHelperUnitTests {

    private lateinit var cards: List<Card>
    @BeforeAll
    fun before_all() {
        cards = listOf(
            Card(Suit.SPADE, Rank.ACE),
            Card(Suit.CLUBS, Rank.ACE),
            Card(Suit.DIAMOND, Rank.ACE),
            Card(Suit.HEART, Rank.ACE),
            Card(Suit.SPADE, Rank.FIVE)
        )
    }

    @Test
    @DisplayName("cardsByRankDesc_ShouldSucceed()")
    fun cardsByRankDesc_ShouldSucceed() {
        // Setup
        val result = cardsByRankDesc(cards = cards)
        val expected = listOf(14, 14, 14, 14, 5)
        val actual   = result.map { it.rank.value }


        // Assertion
        Assertions.assertEquals(expected, actual)
    }

    @Test
    @DisplayName("countByRank_ShouldSucceed()")
    fun countByRank_ShouldSucceed() {
        // Setup
        val result = countByRank(cardsByRankDesc(cards = cards))
        val expected = mapOf(Rank.ACE to 4, Rank.FIVE to 1)

        // Assertion
        Assertions.assertEquals(expected, result)
    }
    @Test
    @DisplayName("ranksByCountThenRankDesc_ShouldSucceed()")
    fun ranksByCountThenRankDesc_ShouldSucceed() {
        // Setup
        val counts = countByRank(cards)
        val entries: List<Map.Entry<Rank, Int>> = ranksByCountThenRankDesc(counts)

        val actual: List<Pair<Rank, Int>> = entries.map { it.key to it.value }
        val expected = listOf(Rank.ACE to 4, Rank.FIVE to 1)

        // Assertion
        Assertions.assertEquals(expected, actual)
    }

    @Test
    @DisplayName("isFlush_ShouldFail()")
    fun isFlush_ShouldFail() {
        // Assertion
        Assertions.assertFalse(isFlush(cards))
    }
}