package models
enum class HandCategory(val strength: Int) {
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIR(3),
    THREE_OF_A_KIND(4),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_OF_A_KIND(8),
    STRAIGHT_FLUSH(9);
}

data class HandScore(
    val handCategory: HandCategory,
    val cards: List<Card>
)


fun findHandScore(cards: List<Card>) : Result<HandScore> {
    //require(cards.size == 5) {"Expected exactly 5 cards. Currently, ${cards.size} are provided"}
    val sortedCards = cards.sortedByDescending { it.rank.value }

    println(sortedCards)

    // For avoiding build error
    return Result.success(HandScore(HandCategory.FOUR_OF_A_KIND, cards))
}