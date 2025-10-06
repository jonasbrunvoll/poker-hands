package services

import domain.Card
import domain.Deck
import kotlin.random.Random

data class DealResult(
    val hands: List<List<Card>>,
)
object Dealer {
    fun deal(
        deck: Deck,
        numHands: Int = 2,
        handSize: Int = 5,
        animate: Boolean = false,
        delayMs: Long = 500L
    ) : Result<DealResult> {
        if(numHands < 1) return Result.failure(IllegalArgumentException("numHands must be >= 1"))
        if(handSize < 1) return Result.failure(IllegalArgumentException("handSize must be >= 1"))

        val neededCards = numHands * handSize
        if (deck.size < neededCards) {
            return Result.failure(IllegalArgumentException("Need at least $neededCards cards, but deck has ${deck.size}"))
        }

        val shuffledDeck = deck.shuffled(Random.Default)
        val hands = List(numHands) { mutableListOf<Card>() }
        var dealt = 0

        for (round in 1..handSize) {
            for (h in 0 until numHands) {
                val card = shuffledDeck[dealt++]
                hands[h].add(card)
            }

            if (animate) {
                println("\r  Round $round/$handSize  →")
                hands.forEachIndexed { index, hand ->
                    println("    H${index+1}: ${hand.joinToString(" ")}")
                }
                println()
                System.out.flush()
                Thread.sleep(delayMs)
            }
        }

        return Result.success(DealResult(hands.map { it.toList() }))
    }

}