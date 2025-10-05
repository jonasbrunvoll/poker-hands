import models.Card
import models.Rank
import models.Suit
import models.findHandScore

/*
    TODO: 1: Lag en Card klasse. Skal inneholde suit (diamonds (D), hearts (H) clubs (C) and spades (S)) og value (2-10, J, Q, K, A)
    TODO: 2: Lag en Deck klasse som skal inneholde fem kort.
    TODO: 3: Lag en en funksjon for å kategorisere pokerhånd + enhetstester.
    TODO: 4: Lag en funksjon for å sammenligne to pokerhender + enhetstester.
    TODO: 5: Oppdatere README.md og publisere repo på github.
*/

fun main() {
    val card1 = Card(Suit.CLUBS, Rank.KING)
    val card2 = Card(Suit.CLUBS, Rank.QUEEN)
    val card3 = Card(Suit.CLUBS, Rank.JACK)
    val card4 = Card(Suit.CLUBS, Rank.TEN)
    val card5 = Card(Suit.CLUBS, Rank.NINE)

    val hand = listOf(card2, card3, card4, card5, card1)

    findHandScore(hand)


    println("$card1 is higher then $card2: ${card1 > card2}")
    println("$card1 is lowe then $card2: ${card1 < card2}")



}
