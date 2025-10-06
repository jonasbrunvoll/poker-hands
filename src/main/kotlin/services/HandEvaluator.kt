package services

import domain.Card
import domain.Hand
import utils.compareHandsInt
import utils.findHandCategory
import utils.findTiebreakers

object HandEvaluator {
    fun evaluateHand(handNumber: Int, hand: List<Card>) : Result<Hand> {
        val category    = findHandCategory(hand)
        val tiebreakers = findTiebreakers(hand, category)

        return Result.success(Hand(
            handNumber  = handNumber,
            handCategory= category,
            tiebreakers = tiebreakers
        ))
    }

    // Returns ALL winners (handles ties)
    fun findWinners(hands: List<Hand>): List<Hand> {
        val winners = mutableListOf(hands.first())
        for (i in 1..<hands.size) {
            val cmp = compareHandsInt(hands[i], winners.first())
            when {
                cmp > 0 -> { winners.clear(); winners.add(hands[i]) } // new best
                cmp == 0 -> winners.add(hands[i])                     // tie with best
            }
        }
        return winners
    }
}
