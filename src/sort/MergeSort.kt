package sort

import kotlin.math.max
import kotlin.math.min

/**
 * Created by Anur IjuoKaruKas on 2019/8/13
 */
object MergeSort {

    fun sort(arr: IntArray): IntArray {
        val size = arr.size
        val lastIndex = max(size - 1, 0)

        val leftStart = 0
        val leftEnd = lastIndex / 2
        val leftSize = leftEnd - leftStart + 1;

        val rightStart = min(leftEnd + 1, lastIndex)
        val rightEnd = lastIndex
        val rightSize = rightEnd - rightStart + 1

        var left = IntArray(leftSize)
        System.arraycopy(arr, leftStart, left, 0, leftSize)
        var right = IntArray(rightSize)
        System.arraycopy(arr, rightStart, right, 0, rightSize)

        println()

        if (size > 1) {
            left = sort(left)
            right = sort(right)
        }

        var leftPointer = 0
        var rightPointer = 0

        val neo = IntArray(size)

        for (i in 0 until size) {
            if (leftPointer == leftSize || (rightPointer < rightSize && right[rightPointer] < left[leftPointer])) {
                neo[i] = right[rightPointer]
                rightPointer++
            } else {
                neo[i] = left[leftPointer]
                leftPointer++
            }
        }

        return neo
    }

    fun printArray(arr: IntArray) {
        print("arr = ")
        for (i in arr) {
            print(",$i ")
        }
        print(",")
        println()
    }
}

fun main() {
    val intArrayOf = intArrayOf(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,234,235,3456,23456,3456,345,12345,234,6,5678,4678)
//    val intArrayOf = intArrayOf(1, 2, 3, 4)
    val sort = MergeSort.sort(intArrayOf)

    println()
}