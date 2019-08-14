package sort

import kotlin.math.max
import kotlin.math.min


/**
 * Created by Anur IjuoKaruKas on 2019/8/13
 */
object QuickSort {
    fun sort(arr: IntArray) {
        doSort(Slice(arr, 0, arr.size - 1))
    }

    fun doSort(slice: Slice) {

        if (slice.doNotNeedToSort()) return
        var leftPointer = slice.leftPointer
        var rightPointer = slice.rightPointer

        println("slice -> leftPo:$leftPointer, rightPo:$rightPointer")

        while (true) {

            while (leftPointer < slice.pivotIndex) {
                if (slice.arr[leftPointer] > slice.pivotVal && !slice.hasBeanExchange(leftPointer)) break
                leftPointer++
            }
            while (rightPointer > leftPointer) {
                if (slice.arr[rightPointer] < slice.pivotVal && !slice.hasBeanExchange(rightPointer)) break
                rightPointer--
            }

            if (leftPointer == slice.pivotIndex && rightPointer == slice.rightPointer) {
                doSort(Slice(slice.arr, slice.start, slice.end - 1))
                break
            }

            if (rightPointer == leftPointer) {
                slice.exchange(rightPointer, slice.pivotIndex)
                doSort(Slice(slice.arr, slice.start, max(rightPointer - 1, slice.start)))
                doSort(Slice(slice.arr, min(slice.end, rightPointer + 1), slice.end))
                break
            } else {
                slice.exchange(rightPointer, leftPointer)
            }
        }
    }
}

class Slice(val arr: IntArray, val start: Int, val end: Int) {
    val pivotIndex = end
    val pivotVal = arr[end]

    val leftPointer = start
    val rightPointer = pivotIndex - 1

    private val exchangeSet = mutableSetOf<Int>();

    fun hasBeanExchange(p: Int): Boolean {
        return exchangeSet.contains(p)
    }

    fun doNotNeedToSort(): Boolean {
        return end - start == 0
    }

    fun exchange(p1: Int, p2: Int) {
        exchangeSet.add(p1)
        exchangeSet.add(p2)

        val temp = arr[p1]
        arr[p1] = arr[p2]
        arr[p2] = temp
    }
}

fun main() {
    val intArrayOf = intArrayOf(3, 5, 8, 1, 2, 9, 4, 7, 45,456,456,234,234,346,456,567,567,234,234,234234,455)
    val sort = QuickSort.sort(intArrayOf)

    println()
}