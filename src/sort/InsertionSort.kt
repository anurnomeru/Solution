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
        if (index == l.size) {
            return
        } else {
            for (i in index - 1 downTo 1) {
                if (l[i] < l[i + 1]) {
                    break
                } else {
                    val temp = l[i - 1]
                    l[i - 1] = l[i]
                    l[i] = temp
                }
            }
        }
        insertion(l, index + 1)
    }
}

fun main() {
    InsertionSort.sort(arrayOf(1, 2, 3, 4213, 34, 3, 45, 33, 6, 456, 22))
}