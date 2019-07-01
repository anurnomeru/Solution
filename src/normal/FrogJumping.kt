package normal

/**
 * Created by Anur IjuoKaruKas on 2019/7/1
 */
class FrogJumping {

    private val cache = mutableMapOf(Pair(1, 1), Pair(2, 2))

    @Synchronized
    fun jumpTo(n: Int): Int {
        var value = 0
        if (cache.containsKey(n)) return cache[n]!! else for (i in 1 until n) value += jumpTo(i)
        cache[n] = value
        return value
    }
}

fun main() {
    val struct = FrogJumping()
    println(struct.jumpTo(30))
}