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

fun main() {
    easy1.main()
}

