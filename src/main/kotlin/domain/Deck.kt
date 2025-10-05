package domain

typealias Deck = List<Card>
fun standard52Deck(): Deck {
    return Suit.entries.flatMap {
        suit -> Rank.entries.map {
            rank -> Card(suit, rank)
        }
    }
}