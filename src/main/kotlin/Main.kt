import domain.*
import services.Dealer
import services.HandEvaluator
import services.HandEvaluator.findWinners

fun main() {
    val deck = standard52Deck()

    val (hands) = Dealer.deal(deck = deck, animate = true)
        .onSuccess { println("✅ Dealt ${it.hands.size} hand(s).") }
        .getOrElse { e ->
            println("❌ Deal failed: ${e.message}")
            return
        }

    val evals: List<Hand> = hands.mapIndexed { index, hand ->
        HandEvaluator.evaluateHand(handNumber = index+1, hand = hand)
            .getOrElse {e ->
                println("❌ Evaluation failed: ${e.message}")
                return
            }
    }

    evals.forEachIndexed { i, h ->
        println("    H${i + 1}: ${h.cards.joinToString(" ")}  | Score → ${h.handCategory}")
    }
    val winners = findWinners(evals)

    winners.forEach {
        println("\n🎉H${it.handNumber} wins!")
    }
}
