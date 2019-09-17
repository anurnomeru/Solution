package normal

import simple_find.Solution
import java.util.LinkedList


/**
 * Created by Anur IjuoKaruKas on 2019/9/17
 *
 * 有一堆扑克牌，将牌堆第一张放到桌子上，再将接下来的牌堆的第一张放到牌底，如此往复；
 *
 *最后桌子上的牌顺序为： (牌底) 1,2,3,4,5,6,7,8,9,10,11,12,13 (牌顶)；
 *
 *问：原来那堆牌的顺序，用函数实现。
 */
object FuckCard {

    fun solution(cards: LinkedList<Int>, cardsBefore: LinkedList<Int> = LinkedList()): LinkedList<Int> {
        if (cards.size == 0) return cardsBefore
        val last = cards.removeLast()

        if (cardsBefore.size > 0) cardsBefore.addFirst(cardsBefore.removeLast())
        cardsBefore.addFirst(last)
        return solution(cards, cardsBefore)
    }
}

fun main() {

    val solution = FuckCard.solution(LinkedList(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)))

    println()
}