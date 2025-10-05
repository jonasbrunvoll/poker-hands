package services

import domain.Card
import domain.HandCategory
import domain.Hand
import domain.Rank

object HandEvaluator {
    fun evaluateHand(handNumber: Int, hand: List<Card>) : Result<Hand> {

        // Order cards from high to low
        val cardsByRankDesc = hand
            .sortedByDescending {
                it.rank.value
            }

        // Count number of each rank. Ex => [A♣, A♠, 9♠, 4♥, 4♠] => {ACE=2, NINE=1, FOUR=2}
        val countByRank = cardsByRankDesc
            .groupingBy {
                it.rank
            }.eachCount()

        // Order by counts then rank. Ex => {ACE=2, NINE=1, FOUR=2} => [ACE=2, FOUR=2, NINE=1]
        val ranksByCountThenRankDesc = countByRank
            .entries
            .sortedWith(compareByDescending<Map.Entry<Rank, Int>> {
                it.value
            }.thenByDescending {
                it.key.value
            })

        // If all suits are identical
        val isFlush = hand.all {
            it.suit == hand.first().suit
        }


        val distinctRanks = cardsByRankDesc.map {
            it.rank.value
        }.distinct()

        // Ex [6, 5, 4, 3, 2] => 6 - 2 = 4,
        val isRegularStraight = distinctRanks.first() - distinctRanks.last() == distinctRanks.size - 1

        val isWheelStraight = run {
            val wheel = setOf(14, 5, 4, 3, 2) // A 5 4 3 2
            distinctRanks.toSet() == wheel
        }

        val isStraight = isRegularStraight || isWheelStraight

        val straightHigh = when {
            isWheelStraight -> 5  // 5-high straight
            isRegularStraight -> distinctRanks.max()
            else -> null
        }

        // Decide category
        val handCategory = when {
            isStraight && isFlush -> HandCategory.STRAIGHT_FLUSH
            ranksByCountThenRankDesc[0].value == 4 -> HandCategory.FOUR_OF_A_KIND
            ranksByCountThenRankDesc[0].value == 3 && ranksByCountThenRankDesc[1].value == 2 -> HandCategory.FULL_HOUSE
            isFlush -> HandCategory.FLUSH
            isStraight -> HandCategory.STRAIGHT
            ranksByCountThenRankDesc[0].value == 3 -> HandCategory.THREE_OF_A_KIND
            ranksByCountThenRankDesc[0].value == 2 && ranksByCountThenRankDesc[1].value == 2 -> HandCategory.TWO_PAIR
            ranksByCountThenRankDesc[0].value == 2 -> HandCategory.ONE_PAIR
            else -> HandCategory.HIGH_CARD
        }

        val tiebreakers: List<Int> = when (handCategory) {
            HandCategory.STRAIGHT_FLUSH -> listOf(straightHigh!!)
            HandCategory.FOUR_OF_A_KIND -> {
                val quad = ranksByCountThenRankDesc.first { it.value == 4 }.key.value
                val kicker = ranksByCountThenRankDesc.first { it.value == 1 }.key.value
                listOf(quad, kicker)
            }
            HandCategory.FULL_HOUSE -> {
                val trips = ranksByCountThenRankDesc.first { it.value == 3 }.key.value
                val pair = ranksByCountThenRankDesc.first { it.value == 2 }.key.value
                listOf(trips, pair)
            }
            HandCategory.FLUSH -> cardsByRankDesc.map { it.rank.value }
            HandCategory.STRAIGHT -> listOf(straightHigh!!)
            HandCategory.THREE_OF_A_KIND -> {
                val trips = ranksByCountThenRankDesc.first { it.value == 3 }.key.value
                val kickers = ranksByCountThenRankDesc.filter { it.value == 1 }.map { it.key.value }
                listOf(trips) + kickers
            }
            HandCategory.TWO_PAIR -> {
                val pairs = ranksByCountThenRankDesc.filter { it.value == 2 }.map { it.key.value }
                val kicker = ranksByCountThenRankDesc.first { it.value == 1 }.key.value
                pairs + kicker
            }
            HandCategory.ONE_PAIR -> {
                val pair = ranksByCountThenRankDesc.first { it.value == 2 }.key.value
                val kickers = ranksByCountThenRankDesc.filter { it.value == 1 }.map { it.key.value }
                listOf(pair) + kickers
            }
            HandCategory.HIGH_CARD -> cardsByRankDesc.map { it.rank.value }
        }

        return Result.success(Hand(handNumber=handNumber, cards = hand, handCategory = handCategory ,tiebreakers = tiebreakers))
    }

    private fun compareTiebreaks(a: List<Int>, b: List<Int>): Int {
    val n = minOf(a.size, b.size)
        for (i in 0 until n) {
            val cmp = a[i].compareTo(b[i])
            if (cmp != 0) return cmp
        }
        return a.size.compareTo(b.size)
    }

    // Compare two hands: >0 if a>b, <0 if a<b, 0 if equal
    private fun compareHandsInt(a: Hand, b: Hand): Int {
        // Prefer .strength if your enum has it; fallback to enum compareTo otherwise
        val catCmp = a.handCategory.strength.compareTo(b.handCategory.strength)
        if (catCmp != 0) return catCmp
        return compareTiebreaks(a.tiebreakers, b.tiebreakers)
    }

    /** Returns ALL winners (handles ties). */
    fun findWinners(hands: List<Hand>): List<Hand> {
        val winners = mutableListOf(hands.first())
        for (i in 1 until hands.size) {
            val cmp = compareHandsInt(hands[i], winners.first())
            when {
                cmp > 0 -> { winners.clear(); winners.add(hands[i]) } // new best
                cmp == 0 -> winners.add(hands[i])                     // tie with best
            }
        }
        return winners
    }
}
