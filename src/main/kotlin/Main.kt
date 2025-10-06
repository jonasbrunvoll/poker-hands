import domain.*
import services.Dealer
import services.HandEvaluator
import services.HandEvaluator.findWinners

fun main() {
    val numHands = 2
    val handSize = 5

    println("Dealing round-robin to $numHands hand(s), $handSize card(s) each:")

    val deck = standard52Deck()

    val (hands) = Dealer.deal(deck = deck, numHands = 2, handSize = 5, animate = true)
        .onSuccess { println("✅ Dealt ${it.hands.size} hand(s).") }
        .getOrElse { e ->
            println("❌ Deal failed: ${e.message}")
            return
        }

    val evaluatedHands: List<Hand> = hands.mapIndexed { index, hand ->
        HandEvaluator.evaluateHand(handNumber = index+1, hand = hand)
            .getOrElse {e ->
                println("❌ Evaluation failed: ${e.message}")
                return
            }
    }

    evaluatedHands.forEachIndexed { i, h ->
        println("    Score H${i + 1}: ${h.handCategory}")
    }

    val winners = findWinners(evaluatedHands)
    winners.forEach {
        println("\n🎉H${it.handNumber} wins!")
    }
}
