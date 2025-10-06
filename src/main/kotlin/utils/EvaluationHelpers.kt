package utils

import domain.Card
import domain.Hand
import domain.HandCategory
import domain.Rank

// Order cards from high to low
fun cardsByRankDesc(cards: List<Card>): List<Card> =
    cards.sortedByDescending { it.rank.value }

// Count number of each rank. Ex => [A♣, A♠, 9♠, 4♥, 4♠] => {ACE=2, NINE=1, FOUR=2}
fun countByRank(cards: List<Card>): Map<Rank, Int> =
    cards.groupingBy {
        it.rank
    }.eachCount()


// Order by counts then rank. Ex => {ACE=2, NINE=1, FOUR=2} => [ACE=2, FOUR=2, NINE=1]
fun ranksByCountThenRankDesc(count:  Map<Rank, Int> ):  List<Map.Entry<Rank, Int>> = count
    .entries
    .sortedWith(compareByDescending<Map.Entry<Rank, Int>> {
        it.value
    }.thenByDescending {
        it.key.value
    })

// If all suits are identical
fun isFlush(hand: List<Card>) = hand.all {
    it.suit == hand.first().suit
}


// Returns straight high (2..14) or null. A-2-3-4-5 → 5.
fun straightHighOrNull(cards: List<Card>): Int? {
    val distinctDesc = cards.map { it.rank.value }.distinct().sortedDescending()

    val isRegular = distinctDesc.first() - distinctDesc.last() == cards.size - 1
    if (isRegular) return distinctDesc.first()

    val wheel = setOf(14, 5, 4, 3, 2) // -> A-2-3-4-5
    if (distinctDesc.toSet() == wheel) {
        return 5
    }
    return null
}


fun findHandCategory(cards: List<Card>): HandCategory {
    val flush = isFlush(cards)
    val straightHigh = straightHighOrNull(cards)
    val counts = ranksByCountThenRankDesc(countByRank(cards))
    return when {
        straightHigh != null && flush -> HandCategory.STRAIGHT_FLUSH
        counts[0].value == 4          -> HandCategory.FOUR_OF_A_KIND
        counts[0].value == 3 && counts[1].value == 2 -> HandCategory.FULL_HOUSE
        flush                          -> HandCategory.FLUSH
        straightHigh != null           -> HandCategory.STRAIGHT
        counts[0].value == 3           -> HandCategory.THREE_OF_A_KIND
        counts[0].value == 2 && counts[1].value == 2 -> HandCategory.TWO_PAIR
        counts[0].value == 2           -> HandCategory.ONE_PAIR
        else                            -> HandCategory.HIGH_CARD
    }
}
fun findTiebreakers(cards: List<Card>, category: HandCategory) : List<Int> {
    val sorted = cardsByRankDesc(cards)
    val counts = ranksByCountThenRankDesc(countByRank(cards))
    val straightHigh = straightHighOrNull(cards)

    return when (category) {
        HandCategory.STRAIGHT_FLUSH -> listOf(straightHigh!!)
        HandCategory.FOUR_OF_A_KIND -> {
            val quad   = counts.first { it.value == 4 }.key.value
            val kicker = counts.first { it.value == 1 }.key.value
            listOf(quad, kicker)
        }
        HandCategory.FULL_HOUSE -> {
            val trips = counts.first { it.value == 3 }.key.value
            val pair  = counts.first { it.value == 2 }.key.value
            listOf(trips, pair)
        }
        HandCategory.FLUSH      -> sorted.map { it.rank.value }
        HandCategory.STRAIGHT   -> listOf(straightHigh!!)
        HandCategory.THREE_OF_A_KIND -> {
            val trips   = counts.first { it.value == 3 }.key.value
            val kickers = counts.filter { it.value == 1 }.map { it.key.value }
            listOf(trips) + kickers
        }
        HandCategory.TWO_PAIR -> {
            val pairs  = counts.filter { it.value == 2 }.map { it.key.value }
            val kicker = counts.first { it.value == 1 }.key.value
            pairs + kicker
        }
        HandCategory.ONE_PAIR -> {
            val pair    = counts.first { it.value == 2 }.key.value
            val kickers = counts.filter { it.value == 1 }.map { it.key.value }
            listOf(pair) + kickers
        }
        HandCategory.HIGH_CARD -> sorted.map { it.rank.value }
    }
}
fun compareHandsInt(a: Hand, b: Hand): Int {
    // Prefer .strength if your enum has it; fallback to enum compareTo otherwise
    val catCmp = a.handCategory.strength.compareTo(b.handCategory.strength)
    if (catCmp != 0) return catCmp
    return compareTiebreaks(a.tiebreakers, b.tiebreakers)
}

fun compareTiebreaks(a: List<Int>, b: List<Int>): Int {
    val n = minOf(a.size, b.size)
    for (i in 0..<n) {
        val cmp = a[i].compareTo(b[i])
        if (cmp != 0) return cmp
    }
    return a.size.compareTo(b.size)
}