import domain.*
import services.Dealer

/*
    TODO: 1: Lag en Card klasse. Skal inneholde suit (diamonds (D), hearts (H) clubs (C) and spades (S)) og value (2-10, J, Q, K, A)
    TODO: 2: Lag en Deck klasse som skal inneholde fem kort.
    TODO: 3: Lag en en funksjon for å kategorisere pokerhånd + enhetstester.
    TODO: 4: Lag en funksjon for å sammenligne to pokerhender + enhetstester.
    TODO: 5: Oppdatere README.md og publisere repo på github.
*/

fun main() {
    val deck = standard52Deck()
    val result = Dealer.deal(deck = deck, animate = false)

    result.fold(
        onSuccess = {(hands, remaining) ->
            hands.forEachIndexed { index, hand ->
                println("\r    H${index + 1}: ${hand.joinToString(" ")}")
            }
            //println("Remaining number of cards in deck: ${remaining.size}")
        },
        onFailure = { e ->
            println("Deal failed: ${e.message}")
        }
    )
}
