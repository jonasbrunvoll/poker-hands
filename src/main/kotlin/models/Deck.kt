package models

import kotlin.random.Random

data class DealResult(
    val hands: List<List<Card>>,
    val remainingDeck: List<Card>
)

fun standard52Deck(): List<Card> {
    return Suit.entries.flatMap {
        suit -> Rank.entries.map {
            rank -> Card(suit, rank)
        }
    }
}

fun createDeckSnapshot(hand: List<Card>): String {
    return hand.joinToString(" ")
}

fun dealHands(
    deck: List<Card>,
    numHands: Int = 2,
    handSize: Int = 5,
    animate: Boolean = false,
    delayMs: Long = 500L
) : DealResult {
    require(numHands >= 1) { "numHands must be >= 1" }
    require(handSize >= 1) { "handSize must be >= 1" }
    val neededCards = numHands * handSize
    require(deck.size >= neededCards) {
        "Need at least $neededCards cards, but deck has ${deck.size}"
    }

    println("Dealing round-robin to $numHands hand(s), $handSize card(s) each:")

    val shuffledDeck = deck.shuffled(Random.Default)
    val hands = List(numHands) { mutableListOf<Card>() }
    var dealt = 0

    for (round in 1..handSize) {
        println("\r  Round $round/$handSize  →")

        for (h in 0 until numHands) {
            val card = shuffledDeck[dealt++]
            hands[h].add(card)
        }

        if (animate) {
            hands.forEachIndexed { index, hand ->
                println("    H${index+1}: ${createDeckSnapshot(hand)}")
            }
            println()
            System.out.flush()
            Thread.sleep(delayMs)
        }
    }

    val remaining = shuffledDeck.drop(neededCards)

    return DealResult(hands.map { it.toList() }, remaining)
}
