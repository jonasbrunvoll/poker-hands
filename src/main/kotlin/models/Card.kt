package models

//TODO: 1: Lag en Card klasse. Skal inneholde suit (diamonds (D), hearts (H) clubs (C) and spades (S)) og value (2-10, J, Q, K, A)
enum class Suit{
    DIAMOND,
    HEART,
    CLUBS,
    SPADE
}
enum class Rank(val value: Int, val symbol: String) {
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(11, "J"),
    QUEEN(12, "Q"),
    KING(13, "K"),
    ACE(14, "A");
    companion object {
        fun fromSymbolToEnum(symbol: String): Rank = when (symbol.uppercase()) {
            "2" -> TWO
            "3" -> THREE
            "4" -> FOUR
            "5" -> FIVE
            "6" -> SIX
            "7" -> SEVEN
            "8" -> EIGHT
            "9" -> NINE
            "10"-> TEN
            "J" -> JACK
            "Q" -> QUEEN
            "K" -> KING
            "A" -> ACE
            else -> error("Unknown rank: $symbol")
        }
    }
}
data class Card(
    val suit: Suit,
    val rank: Rank
) : Comparable<Card> {
    override fun compareTo(other: Card): Int {
        return this.rank.value.compareTo(other.rank.value)
    }
    override fun toString(): String {
        val suitSymbol = when (suit) {
            Suit.DIAMOND -> "♦"
            Suit.HEART -> "♥"
            Suit.CLUBS -> "♣"
            Suit.SPADE -> "♠"
        }
        return "${rank.symbol}$suitSymbol"
    }
}