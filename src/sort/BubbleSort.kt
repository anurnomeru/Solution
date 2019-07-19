package sort

/**
 * Created by Anur IjuoKaruKas on 2019/7/19
 */
object BubbleSort {

    fun sort(l: Array<Int>) {
        doSort(l, 0, 0)
        println()
    }

    private fun doSort(l: Array<Int>, index: Int, pointer: Int) {
        var nextP = pointer
        var nextI = index + 1
        if (index == l.size - 1) {
            if (pointer == l.size - 1) {
                return
            } else {
                nextP = pointer + 1
                nextI = nextP
            }
        } else if (l[index] > l[index + 1]) {
            val temp = l[index]
            l[index] = l[index + 1]
            l[index + 1] = temp
        }
        doSort(l, nextI, nextP)
    }
}

fun main() {
    BubbleSort.sort(arrayOf(1, 2, 3, 4213, 34, 3, 45, 33, 6, 456, 22))
}