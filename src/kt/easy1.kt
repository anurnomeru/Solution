package kt


/**
 * Created by Anur IjuoKaruKas on 2019/6/26
 */
class easy1 {
    companion object {
        private fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
            return { x -> f(g(x)) }
        }

        private fun isOdd(x: Int) = x % 2 != 0

        fun length(s: String) = s.length

        fun main() {
            val oddLength = compose(::isOdd, ::length)
            val strings = listOf("a", "ab", "abc")
            println(strings.filter(oddLength))
        }
    }
}

class Node {
    val value: Int? = null
    val prev: Node? = null
    val next: Node? = null
}

class List(val value: Int, val List: List?)

fun main() {
    val intArrayOf1 = mutableListOf<Int>(1, 2, 3)
    val intArrayOf2 = mutableListOf<Int>(3, 4, 5)

    for (i in intArrayOf1 + intArrayOf2) {

        println(i)
    }
}

