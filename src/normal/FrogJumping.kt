package normal

/**
 * Created by Anur IjuoKaruKas on 2019/7/1
 */
class FrogJumping {

    private val cache = mutableMapOf(Pair(1, 1L), Pair(2, 2L))

    @Synchronized
    fun jumpTo(n: Int): Long {
        var value = 0L
        if (cache.containsKey(n)) return cache[n]!! else for (i in 1 until n) value += jumpTo(i)
        cache[n] = value
        return value
    }
}

fun main() {
    val struct = FrogJumping()
    println(struct.jumpTo(4))
}