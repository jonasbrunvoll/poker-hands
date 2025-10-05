package services

import domain.Card
import domain.HandCategory
import domain.Hand
object HandEvaluator {
    fun evaluate(cards: List<Card>) : Result<Hand> {
        //require(cards.size == 5) {"Expected exactly 5 cards. Currently, ${cards.size} are provided"}
        val sortedCards = cards.sortedByDescending { it.rank.value }

        println(sortedCards)

        // For avoiding build error
        return Result.success(Hand(HandCategory.FOUR_OF_A_KIND, cards))
    }
}
