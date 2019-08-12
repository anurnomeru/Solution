package sort

/**
 * Created by Anur IjuoKaruKas on 2019/7/22
 */
object InsertionSort {

    fun sort(l: Array<Int>) {
        insertion(l, 1)
        println()
    }

    fun insertion(l: Array<Int>, index: Int) {
        when (index == l.size) {
            true -> return
            false -> {
                for (i in index downTo 1) {
                    if (l[i - 1] > l[i]) {
                        val temp = l[i];
                        l[i] = l[i - 1]
                        l[i - 1] = temp
                    } else break
                }
            }
        }
        insertion(l, index + 1)
    }
}

fun main() {
    InsertionSort.sort(arrayOf(1, 2, 3, 4213, 34, 3, 45, 33, 6, 456, 22))
}